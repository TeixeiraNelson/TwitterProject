package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;

public class Tweet extends BaseActivity {

    private TextView username;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        //Get the textview from the xml file and implemente the username with this informations

        username = findViewById(R.id.txtUsernameNew);
        String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ? getString(R.string.info_email) : this.getCurrentUser().getEmail();
        username.setText(email);

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
             /* User user = User.getUserSession(Tweet.this.getApplicationContext());
              String nickname = user.nickname;*/

              //Add the new tweet on the database
             /* db = TweetDatabaseDeux.getAppDatabase(Tweet.this.getApplicationContext());
              int idUser = db.UserDao().getUserId(nickname);
              db.tweetDao().insertAll(new TweetEntityDeux(msg,idUser,hashtags));
              Intent intent = new Intent(Tweet.this.getApplicationContext(), Menu.class);
              startActivity(intent);
              finish();*/

              db = FirebaseFirestore.getInstance();

              Map<String, Object> newTweet = new HashMap<>();
              newTweet.put("Message",msg);
              newTweet.put("Hastags",hashtags);
              newTweet.put("Email",email);
              newTweet.put("Date", System.currentTimeMillis());

              TweetEntityDeux testTweet = new TweetEntityDeux("test TweetEntity","test@tareum.com","#Toutcep",System.currentTimeMillis());

              db.collection("Tweet").document()
                      .set(newTweet)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              Toast.makeText(Tweet.this,"new tweet add", Toast.LENGTH_SHORT).show();

                          }
                      })
                      .addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Toast.makeText(Tweet.this,"fail new tweet add", Toast.LENGTH_SHORT).show();

                          }
                      });

              Intent intent = new Intent(Tweet.this.getApplicationContext(), Menu.class);
              startActivity(intent);
              finish();
          }
      });
    }


}
