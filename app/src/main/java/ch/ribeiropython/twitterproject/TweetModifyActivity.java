package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TweetModifyActivity extends AppCompatActivity {

    private TextView username;
    private TextView tweet;
    private TextView hashtags;

    private String usernameSt;
    private String tweetSt;
    private String hashtagsSt;
    private String idTweet;

    FirebaseFirestore db;

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

        usernameSt = intent.getStringExtra(getResources().getString(R.string.Int_nickname));
        tweetSt = intent.getStringExtra(getResources().getString(R.string.Int_tweet));
        hashtagsSt = intent.getStringExtra(getResources().getString(R.string.Int_hashtags));
        idTweet = intent.getStringExtra(getResources().getString(R.string.Int_idTweet));

        System.out.println("== Arrivé editTWeet -> "+usernameSt+" "+tweetSt+" "+hashtagsSt+" "+idTweet);
        db = FirebaseFirestore.getInstance();

        //Set all the field with extra's informations
        username.setText(usernameSt);
        tweet.setText(tweetSt);
        hashtags.setText(hashtagsSt);

    }

    public void deleteTweet(View o){

        db.collection("Tweet").document(idTweet)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        TweetModifyActivity.this.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TweetModifyActivity.this,"fail delete Tweet", Toast.LENGTH_SHORT).show();
                    }
                });


        this.finish();
    }

    public void modifyTweet(View o){

        String newTweetMsg = tweet.getText().toString();
        String newHashtags = hashtags.getText().toString();


        db.collection("Tweet").document(idTweet)
                .update("Message",newTweetMsg,"Hastags",newHashtags)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        TweetModifyActivity.this.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TweetModifyActivity.this,"fail edit Tweet", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
