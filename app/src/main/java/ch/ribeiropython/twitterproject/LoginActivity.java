package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView sub;


    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPref), MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();


        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        sub = findViewById(R.id.txtSub);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginEmail = email.getText().toString();
                String loginPwd = password.getText().toString();

                checkUserLogin(loginEmail,loginPwd);

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

    private void launchMenu(FirebaseUser currentUser) {
        if(currentUser!=null){
            Intent intentMyAccount = new Intent(getApplicationContext(), Menu.class);
            startActivity(intentMyAccount);
            finish();
        }
    }

    protected void checkUserLogin (String email, String pwd){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/fireblog");


    }
}
