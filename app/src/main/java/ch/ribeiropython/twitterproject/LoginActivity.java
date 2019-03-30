package ch.ribeiropython.twitterproject;

import android.content.Context;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

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


        // Clearing shared preferences on start
        SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPref), MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();



        // Creating a random file to make sure that the initializeDB method is called only once, at the first time the app runs.
        File file = new File(getApplicationContext().getFilesDir(), "binFile.bin");

        if(!file.exists()){
            initializeDB();

            String filename = "binFile.bin";
            String fileContents = "App already launched once";
            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(fileContents.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        sub = findViewById(R.id.txtSub);


        // Setting action to login button, verifies user in the database
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

        // Setting action to subscribe button, starting new activity.
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMyAccount = new Intent(getApplicationContext(), SubscriptionActivity.class);
                startActivity(intentMyAccount);


            }
        });


    }

    // method that checks the user in the database.
    protected boolean checkUserLogin (String email, String pwd){

        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);

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

    // Method that initializes the DB with default data that we chose.
    private void initializeDB(){
        db = TweetDatabaseDeux.getAppDatabase(this);
        boolean duplicates = false;

        ArrayList<UserEntity> listUser = new ArrayList<>();
        listUser.add(new UserEntity("bonjour@test.com","test","The Tweeter Team"));
        listUser.add(new UserEntity("bonjour@test2.com","test2","Nicolas78"));
        listUser.add(new UserEntity("bonjour@test3.com","test3","Arthur_Brandon"));

        for (UserEntity user : listUser) {
            try {
                db.UserDao().insert(new UserEntity(user.getEmail(),user.getPass(),user.getNickname()));
            } catch (SQLiteConstraintException e) {
                duplicates = true;
            }
        }


        ArrayList<TweetEntityDeux> listTweet = new ArrayList<>();
        listTweet.add(new TweetEntityDeux("Hi people, here is the first tweet ever.", 1 , "#TwitterTeam"));
        listTweet.add(new TweetEntityDeux("Hey people, what's up ?", 2 , "#super"));
        listTweet.add(new TweetEntityDeux("J'ai mangé une pomme aujourd'hui.", 3 , "#heyben"));
        listTweet.add(new TweetEntityDeux("Cool to be here !", 2 , "#2emeTweet"));


        for (TweetEntityDeux tweet : listTweet) {
            try {
                db.tweetDao().insertAll(new TweetEntityDeux(tweet.getMessage(),tweet.getIdUser(),tweet.getHashtags()));
            } catch (SQLiteConstraintException e) {
                duplicates = true;
            }
        }
    }
}
