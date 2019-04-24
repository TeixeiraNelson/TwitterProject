package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 12;
    private EditText email, password;
    private TextView sub;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPref), MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("328264475000-1cp50o4fqt3t43slvi288c4j5slmtnja.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            saveGoogleAccount(account);
        }



        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        FirebaseUser currentUser = mAuth.getCurrentUser();
        launchMenu(currentUser);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account);
            saveGoogleAccount(account);


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Toast.makeText(getApplicationContext(), "Error on google log in : " + e.getStatusCode(), Toast.LENGTH_LONG).show();
        }
    }

    private void saveGoogleAccount(GoogleSignInAccount account) {
        // TODO : SAVE USER TO SHARED PREFS

        Intent intentMyAccount = new Intent(getApplicationContext(), Menu.class);
        startActivity(intentMyAccount);
        finish();
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.

                        }

                    }
                });
    }

    protected void checkUserLogin (String email, String pwd){

        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(getApplicationContext(), "You logged in properly", Toast.LENGTH_LONG).show();

                            saveFirebaseUserToSharedPref(email, pwd);
                            Intent intentMyAccount = new Intent(getApplicationContext(), Menu.class);
                            startActivity(intentMyAccount);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Login incorrect", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void saveFirebaseUserToSharedPref(String email, String pwd) {
        // TODO : SAVE USER TO SHARED PREFS

    }
}
