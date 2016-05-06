package co.crtn.www.creationapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.*;
import android.widget.GridLayout.LayoutParams;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.drawable.ColorDrawable;

import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    // Progress Dialog
    private ProgressDialog pDialog;

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
    private static final String TAG_TITLE = "title";
    private static final String TAG_NDESCRIPTION = "ndescription";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_NEWSPIC = "newspic";
    private static final String TAG_JSONSTRING = "jsonstring";

    // products JSONArray
    JSONArray products = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(3);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();
        // Get listview
        //ListView lv = getListView();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://co.crtn.www.creationapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://co.crtn.www.creationapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText("Hello");
            return rootView;
        }
    }

    //------------------------------------------------------------------------------------------------------------------------
    public static class NewsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";

        public NewsFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static NewsFragment newInstance(int sectionNumber) {
            NewsFragment fragment = new NewsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.news_fragment, container, false);
            final ListView lv = (ListView) rootView.findViewById(android.R.id.list);
            // on seleting single product
            // launching Edit Product Screen
            if (lv != null) {
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String json = lv.getItemAtPosition(position).toString();



                        String requiredString = json.substring(json.indexOf("jsonstring=") + 11, json.indexOf("}") + 1);
                        Log.d("string: ", requiredString);


                        try {
                            JSONObject obj = new JSONObject(requiredString);
                            String ndesc = obj.getString("ndescription");
                            String title = obj.getString("title");
                            String newspic = obj.getString("newspic");
                            String author = obj.getString("author");
                            //Log.d("My App", n);
                            AllProductsActivity apa = new AllProductsActivity();
                            apa.ShowPopup(view, container, inflater, title, author, ndesc, newspic);

                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
                        }

                    }

                });
            }
            return rootView;
        }
    }
//-----------------------------------------------------------------------------------------------------------------------------


    public static class StoreFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public StoreFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static StoreFragment newInstance(int sectionNumber) {
            StoreFragment fragment = new StoreFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.store_fragment, container, false);
            final Button shirts = (Button) rootView.findViewById(R.id.btnViewShirts);
            shirts.setText("View Shirts!");
            Button hats = (Button) rootView.findViewById(R.id.btnViewHats);
            hats.setText("View Hats!");
            shirts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
            return rootView;
        }
    }


    //-----------------------------------------------------------------------------------------------------------------------------


    public static class NewsButton extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        Button btnViewProducts;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public NewsButton() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static NewsButton newInstance(int sectionNumber) {
            NewsButton fragment = new NewsButton();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.news_button, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.test_label);
            //textView.setText("store frag!")

            btnViewProducts = (Button) rootView.findViewById(R.id.btnViewProducts);
            btnViewProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Launching All products Activity
                    Intent i = new Intent(getActivity().getApplicationContext(), AllProductsActivity.class);
                    startActivity(i);

                }
            });

            return rootView;

        }
    }


    //-----------------------------------------------------------------------------------------------------------------------------


    public static class NewsTestFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public NewsTestFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static NewsTestFragment newInstance(int sectionNumber) {
            NewsTestFragment fragment = new NewsTestFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.news_test, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.test_label);
            //textView.setText("store frag!");
            //Intent i = new Intent(getActivity(), AllProductsActivity.class);
            //startActivity(i);
            return rootView;
        }
    }


//-----------------------------------------------------------------------------------------------------------------------------

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if (position == 0) {
                new LoadAllProducts().execute();
                return NewsFragment.newInstance(position + 1);
            } else if (position == 1) {

                return StoreFragment.newInstance(position + 1);
            } else {

                return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "NEWS";
                case 1:
                    return "STORE";
                case 2:
                    return "TWITTER";
            }
            return null;
        }
    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading news. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                Log.d("title: ", "1");

                if (success == 1) {
                    Log.d("title: ", "2");
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String pid = c.getString(TAG_PID);
                        String title = c.getString(TAG_TITLE);
                        String author = c.getString(TAG_AUTHOR);
                        String ndescription = c.getString(TAG_NDESCRIPTION);
                        String newspic = c.getString(TAG_NEWSPIC);
                        String jsonstring = c.toString();


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, pid);
                        map.put(TAG_TITLE, title);
                        map.put(TAG_AUTHOR, author);
                        map.put(TAG_NDESCRIPTION, ndescription);
                        map.put(TAG_NEWSPIC, newspic);
                        map.put(TAG_JSONSTRING, jsonstring);

                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            NoNewsActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, productsList,
                            R.layout.list_item, new String[]{TAG_PID,
                            TAG_TITLE, TAG_AUTHOR, TAG_NDESCRIPTION, TAG_NEWSPIC, TAG_JSONSTRING},
                            new int[]{R.id.pid, R.id.title, R.id.author, R.id.ndescription, R.id.newspic, R.id.jsonstring});
                    // updating listview
                    ListView myList = (ListView) findViewById(android.R.id.list);
                    myList.setAdapter(adapter);
                }
            });

        }

    }


}
