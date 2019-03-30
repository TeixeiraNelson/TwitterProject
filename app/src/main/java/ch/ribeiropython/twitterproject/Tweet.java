package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;

public class Tweet extends AppCompatActivity {

    TweetDatabaseDeux db;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        //Get the textview from the xml file and implemente the username with this informations
        username = findViewById(R.id.txtUsernameNew);
        User user = User.getUserSession(Tweet.this.getApplicationContext());
        username.setText(user.nickname);

        //Create a new tweet
      Button sendButton = findViewById(R.id.btnSendTweet);
      sendButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

                //Get the informations of the tweet
              EditText tweetTxt = findViewById(R.id.txtTweetEdit);
              String msg = tweetTxt.getText().toString();
              EditText hashTags = findViewById(R.id.txtHashtagEdit);
              String hashtags = hashTags.getText().toString();


                //get the user informations
              User user = User.getUserSession(Tweet.this.getApplicationContext());
              String nickname = user.nickname;

              //Add the new tweet on the database
              db = TweetDatabaseDeux.getAppDatabase(Tweet.this.getApplicationContext());
              int idUser = db.UserDao().getUserId(nickname);
              db.tweetDao().insertAll(new TweetEntityDeux(msg,idUser,hashtags));
              Intent intent = new Intent(Tweet.this.getApplicationContext(), Menu.class);
              startActivity(intent);
              finish();
          }
      });
    }


}
