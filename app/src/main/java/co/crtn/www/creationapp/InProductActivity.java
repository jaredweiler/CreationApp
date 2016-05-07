package co.crtn.www.creationapp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import org.apache.http.*;

public class InProductActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    //LayoutInflater inflater;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;

    // url to get all products list
    private static String url_all_products = "http://10.0.2.2:8080/android_connect/get_all_products.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_NDESCRIPTION = "ndescription";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_NEWSPIC = "newspic";

    // products JSONArray
    JSONArray products = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_fragment);

        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        //new LoadAllProducts().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        NewsInfoActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_PID, pid);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    public void ShowPopup(View anchorView, final ViewGroup container, final LayoutInflater inflater, int pid, String name, int price, String sizes, String colors) {

        View popupView = inflater.inflate(R.layout.productpopout, container, false);

        //PopupWindow popupWindow = new PopupWindow(popupView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
        PopupWindow popupWindow = new PopupWindow(popupView, 600, 800);

        // Example: If you have a TextView inside `popup_layout.xml`
        TextView tv_title = (TextView) popupView.findViewById(R.id.prodname);
        TextView tv2_author = (TextView) popupView.findViewById(R.id.prodsizes);
        TextView tv3_desc = (TextView) popupView.findViewById(R.id.prodcolors);
        TextView tv4_date = (TextView) popupView.findViewById(R.id.prodprice);

        tv_title.setText(name);
        String sprice = Integer.toString(price);
        String fullprice = ("Price: $" + sprice);
        tv4_date.setText(fullprice);
        String fullsizes = ("Sizes: " + sizes);
        tv2_author.setText(fullsizes);
        String fullcolors = ("Colors: " + colors);
        tv3_desc.setText(fullcolors);
        ImageView img = (ImageView) popupView.findViewById(R.id.prodimage);

        switch (pid) {
            case 3:
                img.setImageResource(R.drawable.civjacket);
                break;
            case 4:
                img.setImageResource(R.drawable.civjacket);
                break;
            case 5:
                img.setImageResource(R.drawable.bernietee);
                break;
            case 6:
                img.setImageResource(R.drawable.ezaletee);
                break;
            case 7:
                img.setImageResource(R.drawable.ninex);
                break;
            case 8:
                img.setImageResource(R.drawable.distee);
                break;
            case 9:
                img.setImageResource(R.drawable.anniversarytee);
                break;
            case 10:
                img.setImageResource(R.drawable.barsticker);
                break;
            case 11:
                img.setImageResource(R.drawable.stickerpack);
                break;
            case 12:
                img.setImageResource(R.drawable.pombeanie);
                break;
            case 13:
                img.setImageResource(R.drawable.noisesweatshirt);
                break;
            case 14:
                img.setImageResource(R.drawable.soulcrew);
                break;
            case 15:
                img.setImageResource(R.drawable.ogcrew);
                break;
            case 16:
                img.setImageResource(R.drawable.varsityhoodie);
                break;
            case 17:
                img.setImageResource(R.drawable.disobeyhoodie);
                break;
            case 18:
                img.setImageResource(R.drawable.wolfhoodie);
                break;
            case 19:
                img.setImageResource(R.drawable.twentyx);
                break;
        }

        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        //popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] + anchorView.getHeight());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

    }

}
