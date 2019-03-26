package ch.ribeiropython.twitterproject;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;

public class Tweet extends AppCompatActivity {

    TweetDatabaseDeux db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);


       ArrayList<TweetEntityDeux> listTweet = new ArrayList<>();
        listTweet.add(new TweetEntityDeux("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat.", 1 , "#2013 #mateub"));
        listTweet.add(new TweetEntityDeux("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat.", 2 , "#2013 #mateub"));
        listTweet.add(new TweetEntityDeux("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat.", 31 , "#2013 #mateub"));

        db = TweetDatabaseDeux.getAppDatabase(this);

        boolean duplicates = false;

        for (TweetEntityDeux tweet : listTweet) {
            try {
                db.tweetDao().insert(tweet);
            } catch (SQLiteConstraintException e) {
                duplicates = true;
            }
        }

        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);

        List<TweetEntityDeux> fruits = db.tweetDao().getAllTweets();
        List<TweetEntityDeux> array = new ArrayList<>();

        List<oneTweet> tgg = new ArrayList<>();
        for (TweetEntityDeux fruit : fruits) {
            array.add(fruit);

        }


        System.out.println(array);
    }

    private void sendNewTweet(View o){

        /*
        TODO : Changer le user en dur et aller le chercher dans les shared Preferences.
         */

        EditText tweetTxt = findViewById(R.id.txtTweetEdit);
        String msg = tweetTxt.getText().toString();

        EditText hashTags = findViewById(R.id.txtHashtag);
        String hashtags = hashTags.getText().toString();

        db = TweetDatabaseDeux.getAppDatabase(this);
        db.tweetDao().insertAll(new TweetEntityDeux(msg,1,hashtags));


    }
}
