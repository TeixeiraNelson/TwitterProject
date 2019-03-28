package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;

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
        User user = User.getUserSession(this.getApplicationContext());
        usernameSt = intent.getStringExtra(getResources().getString(R.string.Int_nickname));
        tweetSt = intent.getStringExtra(getResources().getString(R.string.Int_tweet));
        hashtagsSt = intent.getStringExtra(getResources().getString(R.string.Int_hashtags));
        idTweet = intent.getStringExtra(getResources().getString(R.string.Int_idTweet));

        username.setText(usernameSt);
        tweet.setText(tweetSt);
        hashtags.setText(hashtagsSt);






    }

    public void deleteTweet(View o){
        Intent intent = getIntent();
        User user = User.getUserSession(this.getApplicationContext());

        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
        int userId = db.UserDao().getUserId(user.nickname);
        TweetEntityDeux tweetToDelete = new TweetEntityDeux(tweetSt,userId,hashtagsSt);
        db.tweetDao().delete(tweetToDelete);

        this.finish();
    }

    public void modifyTweet(View o){
        Intent intent = getIntent();
        User user = User.getUserSession(this.getApplicationContext());

        String newTweetMsg = tweet.getText().toString();
        String newHashtags = hashtags.getText().toString();

        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
        int userId = db.UserDao().getUserId(user.nickname);
        TweetEntityDeux tweetToUpdate = new TweetEntityDeux(tweetSt,userId,hashtagsSt);
        db.tweetDao().update(tweetToUpdate);

        this.finish();
    }
}
