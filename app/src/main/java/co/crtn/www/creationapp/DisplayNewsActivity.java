package co.crtn.www.creationapp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import org.apache.http.*;

public class DisplayNewsActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    //LayoutInflater inflater;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;




    private static final String TAG_PID = "pid";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_fragment);

        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        //new LoadAllNews().execute();

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

    public void ShowPopup(View anchorView, final ViewGroup container, final LayoutInflater inflater, String title, String author, String desc, String newspic, int pid) {

        View popupView = inflater.inflate(R.layout.popuplayout, container, false);

        //PopupWindow popupWindow = new PopupWindow(popupView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
        PopupWindow popupWindow = new PopupWindow(popupView, 600, 800);

        // Example: If you have a TextView inside `popup_layout.xml`
        TextView tv_title = (TextView) popupView.findViewById(R.id.poptext1);
        TextView tv2_author = (TextView) popupView.findViewById(R.id.poptext2);
        TextView tv3_desc = (TextView) popupView.findViewById(R.id.poptext3);
        TextView tv4_date = (TextView) popupView.findViewById(R.id.poptextdate);


        tv_title.setText(title);
        String fulldate = ("Date: " + newspic);
        tv4_date.setText(fulldate);
        String fullauthor = ("By: " + author);
        tv2_author.setText(fullauthor);
        tv3_desc.setText(desc);
        ImageView img = (ImageView) popupView.findViewById(R.id.image);

        switch (pid) {
            case 0:
                img.setImageResource(R.drawable.carina);
                break;
            case 1:
                img.setImageResource(R.drawable.dano);
                break;
            case 2:
                img.setImageResource(R.drawable.carina);
                break;
            case 20:
                img.setImageResource(R.drawable.jasmine);
                break;
            case 21:
                img.setImageResource(R.drawable.shamitlathrop);
                break;
            case 22:
                img.setImageResource(R.drawable.hunter);
                break;
            case 23:
                img.setImageResource(R.drawable.sophietie);
                break;
            case 24:
                img.setImageResource(R.drawable.hatnews);
                break;
            case 25:
                img.setImageResource(R.drawable.christina);
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
