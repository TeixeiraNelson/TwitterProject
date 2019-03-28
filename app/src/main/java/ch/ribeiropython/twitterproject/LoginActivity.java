package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;
import ch.ribeiropython.twitterproject.entity.UserEntity;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView sub;
    private TweetDatabaseDeux db;


    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initializeDB();


        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        sub = findViewById(R.id.txtSub);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginEmail = email.getText().toString();
                String loginPwd = password.getText().toString();

                if (checkUserLogin(loginEmail,loginPwd)) {
                    Toast.makeText(getApplicationContext(), "Vous êtes loggé correctement", Toast.LENGTH_LONG).show();

                    Intent intentMyAccount = new Intent(getApplicationContext(), Menu.class);
                    startActivity(intentMyAccount);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Login incorrect", Toast.LENGTH_LONG).show();
                }

            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMyAccount = new Intent(getApplicationContext(), SubscriptionActivity.class);
                startActivity(intentMyAccount);

            }
        });


    }

    protected boolean checkUserLogin (String email, String pwd){

        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);



        List<UserEntity> users = db.UserDao().getAllUsers();

        for (UserEntity fruit : users){
            // Toast.makeText(Menu.this, fruit.getIdUser(), Toast.LENGTH_SHORT).show();
            System.out.println("email : "+fruit.getEmail()+"pwd : "+fruit.getPass());
        }


        UserEntity user =  db.UserDao().getUserLogin(email,pwd);
        if(user!=null){
            Gson gson = new Gson();
            User userc = new User();

            userc.email = user.getEmail();
            userc.nickname = user.getNickname();
            userc.password = user.getPass();

            String userInfoString = gson.toJson(userc);



            SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPref), MODE_PRIVATE).edit();
            editor.putString("user",userInfoString);
            editor.apply();
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    private void initializeDB(){
        db = TweetDatabaseDeux.getAppDatabase(this);
        boolean duplicates = false;

        ArrayList<UserEntity> listUser = new ArrayList<>();
        listUser.add(new UserEntity("bonjour@test.com","test","Test1"));
        listUser.add(new UserEntity("bonjour@test2.com","test2","Test2"));
        listUser.add(new UserEntity("bonjour@test3.com","test3","Test3"));

        for (UserEntity user : listUser) {
            try {
                db.UserDao().insert(new UserEntity(user.getEmail(),user.getPass(),user.getNickname()));
                //db.tweetDao().insertAll(new TweetEntityDeux(tweet.getMessage(),tweet.getIdUser(),tweet.getHashtags()));
            } catch (SQLiteConstraintException e) {
                duplicates = true;
            }
        }


        ArrayList<TweetEntityDeux> listTweet = new ArrayList<>();
        listTweet.add(new TweetEntityDeux("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat.", 1 , "#2013 #mateub"));
        listTweet.add(new TweetEntityDeux("Hey ho ho hey", 2 , "#super"));
        listTweet.add(new TweetEntityDeux("Super nouveau tweet a moi heyheyhey", 3 , "#heyben"));
        listTweet.add(new TweetEntityDeux("je viens de décrouvrir un nouveau réseau social !", 2 , "#2emeTweet #Inshalla"));


        for (TweetEntityDeux tweet : listTweet) {
            try {
                db.tweetDao().insert(tweet);
                //db.tweetDao().insertAll(new TweetEntityDeux(tweet.getMessage(),tweet.getIdUser(),tweet.getHashtags()));
                System.out.println("Ce qu'il entre ===> Pseudo : "+tweet.getIdUser()+" message : "+tweet.getMessage()+" hastags : "+tweet.getHashtags()+" id tweet : "+tweet.getIdTweetEntity());

            } catch (SQLiteConstraintException e) {
                duplicates = true;
            }
        }
    }
}
