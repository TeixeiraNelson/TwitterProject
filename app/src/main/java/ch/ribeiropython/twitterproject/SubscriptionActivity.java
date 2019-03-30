package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;

public class SubscriptionActivity extends AppCompatActivity {

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

        //Check if the email is good or not
        if(verifyEmailSyntax(userEmail) && verifyEmailInUse(userEmail)){
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
            //If no you have to change the email
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_email_syntax), Toast.LENGTH_SHORT ).show();
        }



    }

    private boolean verifyEmailInUse(String userEmail) {

        //check if the user is all ready use

        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
        String usersEmail = db.UserDao().getByEmail(userEmail);

        if(usersEmail!=null){
            return false;
        } else {
            return true;
        }

    }

    private boolean verifyEmailSyntax (String input){
        if (input == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();
        }
    }

    private boolean verifyPasswords ( String password, String password2){

        //Check if the password has more or egal than 6 caractères
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
