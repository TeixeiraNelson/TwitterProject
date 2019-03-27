package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TweetModifyActivity extends AppCompatActivity {

    private TextView username;
    private TextView tweet;
    private TextView hashtags;

    private String usernameSt;
    private String tweetSt;
    private String hashtagsSt;
    private String idTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_modify);

        username = findViewById(R.id.txtUsername2Edit);
        tweet = findViewById(R.id.txtTweetEdit);
        hashtags = findViewById(R.id.txtHashtagEdit);

        Intent intent = getIntent();
        usernameSt = intent.getStringExtra(getResources().getString(R.string.Int_nickname));
        tweetSt = intent.getStringExtra(getResources().getString(R.string.Int_tweet));
        hashtagsSt = intent.getStringExtra(getResources().getString(R.string.Int_hashtags));
        idTweet = intent.getStringExtra(getResources().getString(R.string.Int_idTweet));

        username.setText(usernameSt);
        tweet.setText(tweetSt);
        hashtags.setText(hashtagsSt);






    }

    protected void deleteTweet(){

    }
}
