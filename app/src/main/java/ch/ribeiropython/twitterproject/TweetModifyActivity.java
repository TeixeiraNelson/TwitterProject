package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;

public class TweetModifyActivity extends AppCompatActivity {

    private TextView username;
    private TextView tweet;
    private TextView hashtags;

    private String usernameSt;
    private String tweetSt;
    private String hashtagsSt;
    private int idTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_modify);

        //Get the textview from the xml file
        username = findViewById(R.id.txtUsername2Edit);
        tweet = findViewById(R.id.txtTweetEdit);
        hashtags = findViewById(R.id.txtHashtagEdit);

        //Get all the informations from the extra (the previous activity)
        Intent intent = getIntent();
        User user = User.getUserSession(this.getApplicationContext());
        usernameSt = intent.getStringExtra(getResources().getString(R.string.Int_nickname));
        tweetSt = intent.getStringExtra(getResources().getString(R.string.Int_tweet));
        hashtagsSt = intent.getStringExtra(getResources().getString(R.string.Int_hashtags));
        idTweet = intent.getIntExtra(getResources().getString(R.string.Int_idTweet),0);

        //Set all the field with extra's informations
        username.setText(usernameSt);
        tweet.setText(tweetSt);
        hashtags.setText(hashtagsSt);

    }

    public void deleteTweet(View o){

        //Delete a tweet with the Id of it
        //User user = User.getUserSession(this.getApplicationContext());
        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
        db.tweetDao().deleteTweet(Integer.valueOf(idTweet));

        this.finish();
    }

    public void modifyTweet(View o){
       // Intent intent = getIntent();
        //Take the user of the session
        User user = User.getUserSession(this.getApplicationContext());

       // String newTweetMsg = tweet.getText().toString();
       // String newHashtags = hashtags.getText().toString();

        //Find the user id with is name
        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
        int userId = db.UserDao().getUserId(user.nickname);
        TweetEntityDeux tweetToUpdate = new TweetEntityDeux(tweetSt,userId,hashtagsSt);

        //Edit the tweet with the new text
        tweetToUpdate.setIdTweetEntity(idTweet);
        tweetToUpdate.setMessage(tweet.getText().toString());
        tweetToUpdate.setHashtags(hashtags.getText().toString());
        db.tweetDao().update(tweetToUpdate);
        this.finish();
    }
}
