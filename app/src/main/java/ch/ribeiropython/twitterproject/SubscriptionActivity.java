package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class SubscriptionActivity extends AppCompatActivity {

    private static boolean EmailIsFree = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
    }

    public void checkInputs(View view) {

        //get the informations from the xml file
        EditText emailInput = findViewById(R.id.inscr_emailInput);
        EditText passwordInput = findViewById(R.id.inscr_passwdInput);
        EditText passwordInput2 = findViewById(R.id.inscr_passwdInput2);
        String userEmail = emailInput.getText().toString();
        String userPassword = passwordInput.getText().toString();
        String userPassword2 = passwordInput2.getText().toString();

        //Check if the email is free to use
        //In the firebase Database.
        verifyEmailInUse(userEmail);
        if(verifyEmailSyntax(userEmail) && EmailIsFree){
            if(verifyPasswords(userPassword, userPassword2)){
                //If okay go to the next activity
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_info), Toast.LENGTH_SHORT ).show();
                User user = new User();
                user.email = userEmail;
                user.password = userPassword;
                Intent inscrPart2 = new Intent(getApplicationContext(), Subscription_part2_Activity.class);
                inscrPart2.putExtra("User", user);
                startActivity(inscrPart2);
                finish();
            }
        } else {
            //If not you have to change the email
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_email_syntax), Toast.LENGTH_SHORT ).show();
        }



    }

    private void verifyEmailInUse(String userEmail) {

        /*
        Checks the firebase database if the email is already in use or not
         */

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("User").orderByChild("Email").equalTo(userEmail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    EmailIsFree = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EmailIsFree = true;
            }
        });

    }

    private boolean verifyEmailSyntax (String input){
        /*
            Uses the android util patterns to check the syntax of the email.
         */
        if (input == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();
        }
    }

    private boolean verifyPasswords ( String password, String password2){

        //Check if the password has at least 6 characters.
        if(password.equals(password2)){
            if(password.length()>=6){
                return true;
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_passwd_length), Toast.LENGTH_SHORT ).show();
                return false;
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_passwd_match), Toast.LENGTH_SHORT ).show();
            return false;
        }

    }

}
