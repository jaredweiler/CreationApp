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
    private ProgressDialog pDialog1;
    private ProgressDialog pDialog2;
    private ProgressDialog pDialog3;
    private ProgressDialog pDialog4;
    private ProgressDialog pDialog5;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;
    ArrayList<HashMap<String, String>> shirtsproductsList;
    ArrayList<HashMap<String, String>> jacketsproductsList;
    ArrayList<HashMap<String, String>> sweatersproductsList;
    ArrayList<HashMap<String, String>> hoodiesproductsList;
    ArrayList<HashMap<String, String>> miscproductsList;

    // url to get all products list
    private static String url_all_products = "http://10.0.2.2:8080/android_connect/get_all_products.php";
    private static String url_shirts = "http://10.0.2.2:8080/android_connect/get_shirts.php";
    private static String url_jackets = "http://10.0.2.2:8080/android_connect/get_jackets.php";
    private static String url_sweaters = "http://10.0.2.2:8080/android_connect/get_sweaters.php";
    private static String url_hoodies = "http://10.0.2.2:8080/android_connect/get_hoodies.php";
    private static String url_misc = "http://10.0.2.2:8080/android_connect/get_misc.php";

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
    private static final String TAG_PRICE = "price";
    private static final String TAG_SIZES = "sizes";
    private static final String TAG_COLORS = "colors";

    // products JSONArray
    JSONArray products = null;
    JSONArray shirtsproducts = null;
    JSONArray jacketsproducts = null;
    JSONArray sweatersproducts = null;
    JSONArray hoodiesproducts = null;
    JSONArray miscproducts = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //new LoadShirts().execute();

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
        shirtsproductsList = new ArrayList<HashMap<String, String>>();
        jacketsproductsList = new ArrayList<HashMap<String, String>>();
        sweatersproductsList = new ArrayList<HashMap<String, String>>();
        hoodiesproductsList = new ArrayList<HashMap<String, String>>();
        miscproductsList = new ArrayList<HashMap<String, String>>();
        // Get listview
        //ListView lv = getListView();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        new LoadShirts().execute();
        new LoadJackets().execute();
        new LoadSweaters().execute();
        new LoadHoodies().execute();
        new LoadMisc().execute();
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
            final View rootView = inflater.inflate(R.layout.shirts_list, container, false);
            //final ListView lv = (ListView) rootView.findViewById(R.id.shirtslist);
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
                            String stringid = obj.getString("pid");
                            int pid = Integer.parseInt(stringid);
                            //Log.d("My App", n);
                            AllProductsActivity apa = new AllProductsActivity();
                            apa.ShowPopup(view, container, inflater, title, author, ndesc, newspic, pid);

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
        public View onCreateView(final LayoutInflater inflater,final ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.store_fragment, container, false);
            final Button shirts = (Button) rootView.findViewById(R.id.btnViewShirts);
            final Button misc = (Button) rootView.findViewById(R.id.btnViewMisc);
            final Button sweaters = (Button) rootView.findViewById(R.id.btnViewSweaters);
            final Button hoodies = (Button) rootView.findViewById(R.id.btnViewHoodies);
            final Button all = (Button) rootView.findViewById(R.id.btnViewAll);
            final Button jackets = (Button) rootView.findViewById(R.id.btnViewJackets);
            //final View listView = inflater.inflate(R.layout.shirts_list, container, false);
            //new LoadShirts().execute();
            /*
            try {
                Thread.sleep(5000);
                // Do some stuff
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
            Log.d("thread: ", "2");
            //final ListView LV = (ListView) container.findViewById(R.id.shirtslist1);
            */


            misc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView LV = (ListView) container.findViewById(R.id.misclist);
                    final GridLayout GL1 = (GridLayout) container.findViewById(R.id.gl1);
                    final Button back = (Button) container.findViewById(R.id.miscbackbutton);
                    final GridLayout miscgrid = (GridLayout) container.findViewById(R.id.miscgrid);
                    back.setClickable(true);
                    miscgrid.setVisibility(View.VISIBLE);
                    GL1.setVisibility(View.INVISIBLE);
                    if (LV != null) {
                        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                String json = LV.getItemAtPosition(position).toString();
                                String requiredString = json.substring(json.indexOf("jsonstring=") + 11, json.indexOf("}") + 1);
                                Log.d("stringmisc: ", requiredString);


                                try {
                                    JSONObject obj = new JSONObject(requiredString);
                                    String name = obj.getString("name");
                                    String price = obj.getString("price");
                                    String sizes = obj.getString("sizes");
                                    String colors = obj.getString("colors");
                                    String stringid = obj.getString("pid");
                                    int pid = Integer.parseInt(stringid);
                                    int priceint = Integer.parseInt(price);

                                    InProductActivity ipa = new InProductActivity();
                                    ipa.ShowPopup(view, container, inflater, pid, name, priceint, sizes, colors);

                                } catch (Throwable t) {
                                    Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
                                }
                            }

                        });
                    }

                    back.setOnClickListener(new View.OnClickListener() {
                        //shirts.setText("dsfgsfd");
                        @Override
                        public void onClick(View view) {
                            miscgrid.setVisibility(View.INVISIBLE);
                            GL1.setVisibility(View.VISIBLE);
                        }

                    });

                }
            });

            shirts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView LV = (ListView) container.findViewById(R.id.shirtslist1);
                    final GridLayout GL1 = (GridLayout) container.findViewById(R.id.gl1);
                    final Button back = (Button) container.findViewById(R.id.shirtsbackbutton);
                    final GridLayout shirtsgrid = (GridLayout) container.findViewById(R.id.shirtsgrid);
                    back.setClickable(true);
                    shirtsgrid.setVisibility(View.VISIBLE);
                    GL1.setVisibility(View.INVISIBLE);
                    if (LV != null) {
                        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                String json = LV.getItemAtPosition(position).toString();
                                String requiredString = json.substring(json.indexOf("jsonstring=") + 11, json.indexOf("}") + 1);
                                Log.d("stringshirts: ", requiredString);
                                try {
                                    JSONObject obj = new JSONObject(requiredString);
                                    String name = obj.getString("name");
                                    String price = obj.getString("price");
                                    String sizes = obj.getString("sizes");
                                    String colors = obj.getString("colors");
                                    String stringid = obj.getString("pid");
                                    int pid = Integer.parseInt(stringid);
                                    int priceint = Integer.parseInt(price);

                                    InProductActivity ipa = new InProductActivity();
                                    ipa.ShowPopup(view, container, inflater, pid, name, priceint, sizes, colors);

                                } catch (Throwable t) {
                                    Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
                                }

                            }

                        });
                    }

                    back.setOnClickListener(new View.OnClickListener() {
                        //shirts.setText("dsfgsfd");
                        @Override
                        public void onClick (View view){
                            shirtsgrid.setVisibility(View.INVISIBLE);
                            GL1.setVisibility(View.VISIBLE);
                        }

                    });

                }
            });
            sweaters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView LV = (ListView) container.findViewById(R.id.sweaterslist);
                    final GridLayout GL1 = (GridLayout) container.findViewById(R.id.gl1);
                    final Button back = (Button) container.findViewById(R.id.sweatersbackbutton);
                    final GridLayout sweatersgrid = (GridLayout) container.findViewById(R.id.sweatersgrid);
                    back.setClickable(true);
                    sweatersgrid.setVisibility(View.VISIBLE);
                    GL1.setVisibility(View.INVISIBLE);
                    if (LV != null) {
                        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                String json = LV.getItemAtPosition(position).toString();
                                String requiredString = json.substring(json.indexOf("jsonstring=") + 11, json.indexOf("}") + 1);
                                Log.d("stringsweaters: ", requiredString);
                                try {
                                    JSONObject obj = new JSONObject(requiredString);
                                    String name = obj.getString("name");
                                    String price = obj.getString("price");
                                    String sizes = obj.getString("sizes");
                                    String colors = obj.getString("colors");
                                    String stringid = obj.getString("pid");
                                    int pid = Integer.parseInt(stringid);
                                    int priceint = Integer.parseInt(price);

                                    InProductActivity ipa = new InProductActivity();
                                    ipa.ShowPopup(view, container, inflater, pid, name, priceint, sizes, colors);

                                } catch (Throwable t) {
                                    Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
                                }
                            }

                        });
                    }

                    back.setOnClickListener(new View.OnClickListener() {
                        //shirts.setText("dsfgsfd");
                        @Override
                        public void onClick(View view) {
                            sweatersgrid.setVisibility(View.INVISIBLE);
                            GL1.setVisibility(View.VISIBLE);
                        }

                    });

                }
            });
            hoodies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView LV = (ListView) container.findViewById(R.id.hoodieslist);
                    final GridLayout GL1 = (GridLayout) container.findViewById(R.id.gl1);
                    final Button back = (Button) container.findViewById(R.id.hoodiesbackbutton);
                    final GridLayout hoodiesgrid = (GridLayout) container.findViewById(R.id.hoodiesgrid);
                    back.setClickable(true);
                    hoodiesgrid.setVisibility(View.VISIBLE);
                    GL1.setVisibility(View.INVISIBLE);
                    if (LV != null) {
                        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                String json = LV.getItemAtPosition(position).toString();
                                String requiredString = json.substring(json.indexOf("jsonstring=") + 11, json.indexOf("}") + 1);
                                Log.d("stringjackets: ", requiredString);
                                try {
                                    JSONObject obj = new JSONObject(requiredString);
                                    String name = obj.getString("name");
                                    String price = obj.getString("price");
                                    String sizes = obj.getString("sizes");
                                    String colors = obj.getString("colors");
                                    String stringid = obj.getString("pid");
                                    int pid = Integer.parseInt(stringid);
                                    int priceint = Integer.parseInt(price);

                                    InProductActivity ipa = new InProductActivity();
                                    ipa.ShowPopup(view, container, inflater, pid, name, priceint, sizes, colors);

                                } catch (Throwable t) {
                                    Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
                                }

                            }

                        });
                    }

                    back.setOnClickListener(new View.OnClickListener() {
                        //shirts.setText("dsfgsfd");
                        @Override
                        public void onClick(View view) {
                            hoodiesgrid.setVisibility(View.INVISIBLE);
                            GL1.setVisibility(View.VISIBLE);
                        }

                    });

                }
            });
            jackets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView LV = (ListView) container.findViewById(R.id.jacketslist);
                    final GridLayout GL1 = (GridLayout) container.findViewById(R.id.gl1);
                    final Button back = (Button) container.findViewById(R.id.jacketsbackbutton);
                    final GridLayout jacketsgrid = (GridLayout) container.findViewById(R.id.jacketsgrid);
                    back.setClickable(true);
                    jacketsgrid.setVisibility(View.VISIBLE);
                    GL1.setVisibility(View.INVISIBLE);
                    if (LV != null) {
                        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                String json = LV.getItemAtPosition(position).toString();
                                String requiredString = json.substring(json.indexOf("jsonstring=") + 11, json.indexOf("}") + 1);
                                Log.d("stringjackets: ", requiredString);
                                try {
                                    JSONObject obj = new JSONObject(requiredString);
                                    String name = obj.getString("name");
                                    String price = obj.getString("price");
                                    String sizes = obj.getString("sizes");
                                    String colors = obj.getString("colors");
                                    String stringid = obj.getString("pid");
                                    int pid = Integer.parseInt(stringid);
                                    int priceint = Integer.parseInt(price);

                                    InProductActivity ipa = new InProductActivity();
                                    ipa.ShowPopup(view, container, inflater, pid, name, priceint, sizes, colors);

                                } catch (Throwable t) {
                                    Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
                                }

                            }

                        });
                    }

                    back.setOnClickListener(new View.OnClickListener() {
                        //shirts.setText("dsfgsfd");
                        @Override
                        public void onClick (View view){
                            jacketsgrid.setVisibility(View.INVISIBLE);
                            GL1.setVisibility(View.VISIBLE);
                        }

                    });

                }
            });
            all.setOnClickListener(new View.OnClickListener() {
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
            final View rootView = inflater.inflate(R.layout.shirts_list, container, false);
            final ListView lv = (ListView) rootView.findViewById(R.id.shirtslist);




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
            pDialog.setMessage("Loading NEWS. Please wait...");
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


                if (success == 1) {

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

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadShirts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(MainActivity.this);
            pDialog1.setMessage("Loading shirts. Please wait...");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(false);
            pDialog1.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_shirts, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Shirts: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                Log.d("shirts: ", "1");

                if (success == 1) {
                    Log.d("shirts: ", "2");
                    // products found
                    // Getting Array of Products
                    shirtsproducts = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < shirtsproducts.length(); i++) {
                        JSONObject c = shirtsproducts.getJSONObject(i);

                        // Storing each json item in variable
                        String name = c.getString(TAG_NAME);
                        String pid = c.getString(TAG_PID);
                        String price = c.getString(TAG_PRICE);
                        String sizes = c.getString(TAG_SIZES);
                        String colors = c.getString(TAG_COLORS);

                        String jsonstring = c.toString();



                        // creating new HashMap
                        HashMap<String, String> shirtsmap = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        shirtsmap.put(TAG_NAME, name);
                        shirtsmap.put(TAG_PID, pid);
                        shirtsmap.put(TAG_PRICE, price);
                        shirtsmap.put(TAG_SIZES, sizes);
                        shirtsmap.put(TAG_COLORS, colors);

                        shirtsmap.put(TAG_JSONSTRING, jsonstring);

                        // adding HashList to ArrayList
                        shirtsproductsList.add(shirtsmap);
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
            pDialog1.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, shirtsproductsList,
                            R.layout.shirts_list_item, new String[]{TAG_NAME,
                            TAG_PID, TAG_PRICE, TAG_SIZES, TAG_COLORS, TAG_JSONSTRING},
                            new int[]{R.id.name, R.id.pid, R.id.price, R.id.sizes, R.id.colors, R.id.jsonstring});
                    // updating listview
                    ListView myList = (ListView) findViewById(R.id.shirtslist1);
                    myList.setAdapter(adapter);
                    Log.d("thread: ", "1");
                }
            });

        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadJackets extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(MainActivity.this);
            pDialog2.setMessage("Loading jackets. Please wait...");
            pDialog2.setIndeterminate(false);
            pDialog2.setCancelable(false);
            pDialog2.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_jackets, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Jackets: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                Log.d("jackets: ", "1");

                if (success == 1) {
                    Log.d("jackets: ", "2");
                    // products found
                    // Getting Array of Products
                    jacketsproducts = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < jacketsproducts.length(); i++) {
                        JSONObject c = jacketsproducts.getJSONObject(i);

                        // Storing each json item in variable
                        String name = c.getString(TAG_NAME);
                        String pid = c.getString(TAG_PID);
                        String price = c.getString(TAG_PRICE);
                        String sizes = c.getString(TAG_SIZES);
                        String colors = c.getString(TAG_COLORS);

                        String jsonstring = c.toString();



                        // creating new HashMap
                        HashMap<String, String> jacketsmap = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        jacketsmap.put(TAG_NAME, name);
                        jacketsmap.put(TAG_PID, pid);
                        jacketsmap.put(TAG_PRICE, price);
                        jacketsmap.put(TAG_SIZES, sizes);
                        jacketsmap.put(TAG_COLORS, colors);

                        jacketsmap.put(TAG_JSONSTRING, jsonstring);

                        // adding HashList to ArrayList
                        jacketsproductsList.add(jacketsmap);
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
            pDialog2.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, jacketsproductsList,
                            R.layout.jackets_list_item, new String[]{TAG_NAME,
                            TAG_PID, TAG_PRICE, TAG_SIZES, TAG_COLORS, TAG_JSONSTRING},
                            new int[]{R.id.name, R.id.pid, R.id.price, R.id.sizes, R.id.colors, R.id.jsonstring});
                    // updating listview
                    ListView myList = (ListView) findViewById(R.id.jacketslist);
                    myList.setAdapter(adapter);
                    Log.d("thread: ", "1");
                }
            });

        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadSweaters extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog3 = new ProgressDialog(MainActivity.this);
            pDialog3.setMessage("Loading sweaters. Please wait...");
            pDialog3.setIndeterminate(false);
            pDialog3.setCancelable(false);
            pDialog3.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_sweaters, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Sweaters: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                Log.d("sweaters: ", "1");

                if (success == 1) {
                    Log.d("sweaters: ", "2");
                    // products found
                    // Getting Array of Products
                    sweatersproducts = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < sweatersproducts.length(); i++) {
                        JSONObject c = sweatersproducts.getJSONObject(i);

                        // Storing each json item in variable
                        String name = c.getString(TAG_NAME);
                        String pid = c.getString(TAG_PID);
                        String price = c.getString(TAG_PRICE);
                        String sizes = c.getString(TAG_SIZES);
                        String colors = c.getString(TAG_COLORS);

                        String jsonstring = c.toString();



                        // creating new HashMap
                        HashMap<String, String> sweatersmap = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        sweatersmap.put(TAG_NAME, name);
                        sweatersmap.put(TAG_PID, pid);
                        sweatersmap.put(TAG_PRICE, price);
                        sweatersmap.put(TAG_SIZES, sizes);
                        sweatersmap.put(TAG_COLORS, colors);

                        sweatersmap.put(TAG_JSONSTRING, jsonstring);

                        // adding HashList to ArrayList
                        sweatersproductsList.add(sweatersmap);
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
            pDialog3.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, sweatersproductsList,
                            R.layout.sweaters_list_item, new String[]{TAG_NAME,
                            TAG_PID, TAG_PRICE, TAG_SIZES, TAG_COLORS, TAG_JSONSTRING},
                            new int[]{R.id.name, R.id.pid, R.id.price, R.id.sizes, R.id.colors, R.id.jsonstring});
                    // updating listview
                    ListView myList = (ListView) findViewById(R.id.sweaterslist);
                    myList.setAdapter(adapter);
                    Log.d("thread: ", "1");
                }
            });

        }

    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadHoodies extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog4 = new ProgressDialog(MainActivity.this);
            pDialog4.setMessage("Loading sweaters. Please wait...");
            pDialog4.setIndeterminate(false);
            pDialog4.setCancelable(false);
            pDialog4.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_hoodies, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Hoodies: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);


                if (success == 1) {

                    // products found
                    // Getting Array of Products
                    hoodiesproducts = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < hoodiesproducts.length(); i++) {
                        JSONObject c = hoodiesproducts.getJSONObject(i);

                        // Storing each json item in variable
                        String name = c.getString(TAG_NAME);
                        String pid = c.getString(TAG_PID);
                        String price = c.getString(TAG_PRICE);
                        String sizes = c.getString(TAG_SIZES);
                        String colors = c.getString(TAG_COLORS);

                        String jsonstring = c.toString();



                        // creating new HashMap
                        HashMap<String, String> hoodiesmap = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        hoodiesmap.put(TAG_NAME, name);
                        hoodiesmap.put(TAG_PID, pid);
                        hoodiesmap.put(TAG_PRICE, price);
                        hoodiesmap.put(TAG_SIZES, sizes);
                        hoodiesmap.put(TAG_COLORS, colors);

                        hoodiesmap.put(TAG_JSONSTRING, jsonstring);

                        // adding HashList to ArrayList
                        hoodiesproductsList.add(hoodiesmap);
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
            pDialog4.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, hoodiesproductsList,
                            R.layout.hoodies_list_item, new String[]{TAG_NAME,
                            TAG_PID, TAG_PRICE, TAG_SIZES, TAG_COLORS, TAG_JSONSTRING},
                            new int[]{R.id.name, R.id.pid, R.id.price, R.id.sizes, R.id.colors, R.id.jsonstring});
                    // updating listview
                    ListView myList = (ListView) findViewById(R.id.hoodieslist);
                    myList.setAdapter(adapter);

                }
            });

        }

    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadMisc extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog5 = new ProgressDialog(MainActivity.this);
            pDialog5.setMessage("Loading misc. Please wait...");
            pDialog5.setIndeterminate(false);
            pDialog5.setCancelable(false);
            pDialog5.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_misc, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Misc: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);


                if (success == 1) {

                    // products found
                    // Getting Array of Products
                    miscproducts = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < miscproducts.length(); i++) {
                        JSONObject c = miscproducts.getJSONObject(i);

                        // Storing each json item in variable
                        String name = c.getString(TAG_NAME);
                        String pid = c.getString(TAG_PID);
                        String price = c.getString(TAG_PRICE);
                        String sizes = c.getString(TAG_SIZES);
                        String colors = c.getString(TAG_COLORS);

                        String jsonstring = c.toString();



                        // creating new HashMap
                        HashMap<String, String> miscmap = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        miscmap.put(TAG_NAME, name);
                        miscmap.put(TAG_PID, pid);
                        miscmap.put(TAG_PRICE, price);
                        miscmap.put(TAG_SIZES, sizes);
                        miscmap.put(TAG_COLORS, colors);

                        miscmap.put(TAG_JSONSTRING, jsonstring);

                        // adding HashList to ArrayList
                        miscproductsList.add(miscmap);
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
            pDialog5.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, miscproductsList,
                            R.layout.misc_list_item, new String[]{TAG_NAME,
                            TAG_PID, TAG_PRICE, TAG_SIZES, TAG_COLORS, TAG_JSONSTRING},
                            new int[]{R.id.name, R.id.pid, R.id.price, R.id.sizes, R.id.colors, R.id.jsonstring});
                    // updating listview
                    ListView myList = (ListView) findViewById(R.id.misclist);
                    myList.setAdapter(adapter);

                }
            });

        }

    }


}
