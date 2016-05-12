package co.crtn.www.creationapp;


import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

//import com.javapapers.android.util.AndroidNetworkUtility;

public class TwitterActivity extends ListActivity {

    //This is name of the twitter account you would like to display
    final static String twitterScreenName = "crtnco";

    //this is the name of the calling activity
    final static String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();

        if (androidNetworkUtility.isConnected(this)) {

            //Executing thread to grab tweets
            new TwitterAsyncTask().execute(twitterScreenName,this);

        } else {
            Log.v(TAG, "Network not Available!");
        }
    }
}
