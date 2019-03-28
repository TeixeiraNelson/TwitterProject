package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;
import ch.ribeiropython.twitterproject.entity.UserEntity;

public class subscription_part3_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_part3_);


        TextView agreements = findViewById(R.id.inscr_terms_txt);
        agreements.setText(R.string.termsText);
        agreements.setMovementMethod(new ScrollingMovementMethod());


        Button btn = findViewById(R.id.inscr_btn_next3);
        btn.setVisibility(View.VISIBLE);

        /*
        Check box events
         */



        CheckBox agreeChkBx = ( CheckBox ) findViewById( R.id.checkBox_terms );
        agreeChkBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    Button btn2 = findViewById(R.id.inscr_btn_next3);
                    btn2.setEnabled(true);
                }

            }
        });



    }

    public void startApp(View view){
        Bundle extras = getIntent().getExtras();
        User user;
        if (extras != null) {
            user = (User) getIntent().getSerializableExtra("User");
            saveToDataBase(user);
            saveUserToSession(user);
        }





        /*
        Launching app.
         */
        Intent intentMyAccount = new Intent(getApplicationContext(), Menu.class);
        startActivity(intentMyAccount);
        finish();

    }

    public void saveToDataBase(User user){
        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);

        UserEntity userEntity = new UserEntity(user.email,user.password,user.nickname);

        db.UserDao().insert(userEntity);
    }

    public void saveUserToSession(User user){

        Gson gson = new Gson();
        String userInfoString = gson.toJson(user);



        SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPref), MODE_PRIVATE).edit();
        editor.putString("user",userInfoString);
        editor.apply();
        editor.commit();
    }
}
