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

        username = findViewById(R.id.txtUsernameNew);


        User user = User.getUserSession(Tweet.this.getApplicationContext());

        username.setText(user.nickname);

      /* ArrayList<TweetEntityDeux> listTweet = new ArrayList<>();
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
        */


      Button sendButton = findViewById(R.id.btnDeleteTweet);
      sendButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              EditText tweetTxt = findViewById(R.id.txtTweetEdit);
              String msg = tweetTxt.getText().toString();

              EditText hashTags = findViewById(R.id.txtHashtagEdit);
              String hashtags = hashTags.getText().toString();



              User user = User.getUserSession(Tweet.this.getApplicationContext());

              String nickname = user.nickname;



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
