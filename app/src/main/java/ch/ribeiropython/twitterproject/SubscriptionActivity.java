package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
    }

    public void checkInputs(View view) {
        /*
        Checks the user inputs before sending information to the next step
         */

        /*
        Checking Email
         */

        EditText emailInput = findViewById(R.id.inscr_emailInput);

        EditText passwordInput = findViewById(R.id.inscr_passwdInput);
        EditText passwordInput2 = findViewById(R.id.inscr_passwdInput2);

        String userEmail = emailInput.getText().toString();
        String userPassword = passwordInput.getText().toString();
        String userPassword2 = passwordInput2.getText().toString();

        if(verifyEmailSyntax(userEmail)){
            if(verifyPasswords(userPassword, userPassword2)){
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
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_email_syntax), Toast.LENGTH_SHORT ).show();
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
