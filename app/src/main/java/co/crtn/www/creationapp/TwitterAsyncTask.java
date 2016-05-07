package co.crtn.www.creationapp;

import android.app.ListActivity;
import android.text.method.LinkMovementMethod;
import android.widget.ArrayAdapter;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

//import com.javapapers.social.twitter.TwitterAPI;
//import com.javapapers.social.twitter.TwitterTweet;

import java.util.ArrayList;

public class TwitterAsyncTask extends AsyncTask<Object, Void, ArrayList<TwitterTweet>> {
    ListActivity callerActivity;

    final static String TWITTER_API_KEY = "DdWPHF1DAIo5vw9jSC7foL5ZS";
    final static String TWITTER_API_SECRET = "fUoPZELAEw4XK3NOdPxMLfxv7V7ydCYhynVEY9e1V1FvzLdUmz";

    @Override
    protected ArrayList<TwitterTweet> doInBackground(Object... params) {
        ArrayList<TwitterTweet> twitterTweets = null;
        callerActivity = (ListActivity) params[1];
        if (params.length > 0) {
            TwitterAPI twitterAPI = new TwitterAPI(TWITTER_API_KEY,TWITTER_API_SECRET);
            twitterTweets = twitterAPI.getTwitterTweets(params[0].toString());
        }
        return twitterTweets;
    }

    @Override
    protected void onPostExecute(ArrayList<TwitterTweet> twitterTweets) {
        ArrayAdapter<TwitterTweet> adapter =
                new ArrayAdapter<TwitterTweet>(callerActivity, R.layout.twitter_tweets_list,
                        R.id.listTextView, twitterTweets);
        callerActivity.setListAdapter(adapter);
        ListView lv = callerActivity.getListView();
        lv.setDividerHeight(0);
        //lv.setDivider(this.getResources().getDrawable(android.R.color.transparent));
        lv.setBackgroundColor(callerActivity.getResources().getColor(R.color.Twitter_blue));
    }
}
