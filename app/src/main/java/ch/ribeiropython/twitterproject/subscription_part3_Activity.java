package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class subscription_part3_Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;

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
        /*
            Method that starts the app (menu activity)
         */
        Bundle extras = getIntent().getExtras();
        User user;
        if (extras != null) {
            user = (User) getIntent().getSerializableExtra("User");
            registerUser(user);
        }


    }

    public void registerUser(final User user){
        /*
            Registering the new user in the firebase database
         */


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            signUserIn(user);



                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(subscription_part3_Activity.this, "Subscription failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });




    }

    private void signUserIn(final User user) {
        mAuth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            System.out.println("User signed in !!!!");


                            runMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(subscription_part3_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void runMainActivity() {

        Intent intentMyAccount = new Intent(getApplicationContext(), Menu.class);
        startActivity(intentMyAccount);
        finish();

    }

}
