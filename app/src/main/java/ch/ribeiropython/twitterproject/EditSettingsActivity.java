package ch.ribeiropython.twitterproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditSettingsActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings);
        updateUIWhenCreating();
    }

    private void updateUIWhenCreating(){

        if (this.getCurrentUser() != null){
            TextView usernameTextView = findViewById(R.id.txtUsername);

            //Get email & username from Firebase
            String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ? getString(R.string.info_email) : this.getCurrentUser().getEmail();

            //Update views with data
            usernameTextView.setText(email);
        }
    }

    public void ChangeEmail (View o){
        /*
            Method that is called by the button to change the email in the settings activity
            The method will update the new email of the user in the database.

            it checkes also the email syntax.
         */

        EditText email = findViewById(R.id.txtEmail);
       /* TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
        User user = User.getUserSession(this.getApplicationContext());

        int userId = db.UserDao().getUserId(user.nickname);
        if(db.UserDao().nbEmail(email.getText().toString())==0)
        {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                db.UserDao().updateEmail(email.getText().toString(), userId);
                Toast.makeText(EditSettingsActivity.this, "Email updated.", Toast.LENGTH_SHORT).show();
                EditSettingsActivity.this.finish();
            } else {
                Toast.makeText(EditSettingsActivity.this, "Email syntax incorrect.", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(EditSettingsActivity.this, "Email all ready use.", Toast.LENGTH_SHORT).show();
        }*/


    }

    public void ChangePassword (View o){

        /*
            Method called by the change password button, that updates the new password of the user in the database

           The method verifies as well that both passwords match and that the actual password is correct.
         */
        EditText actualPassword = findViewById(R.id.txtOldPassword);
        EditText Password1 = findViewById(R.id.txtNewPassword);
        EditText Password2 = findViewById(R.id.txtNewPassword2);

        User user = User.getUserSession(this.getApplicationContext());
        if(actualPassword.getText().toString().equals(user.password)){
            if(Password1.getText().toString().equals(Password2.getText().toString())){
               /* TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
                int userId = db.UserDao().getUserId(user.nickname);
                db.UserDao().updatePassword(userId, Password1.getText().toString());
                Toast.makeText(EditSettingsActivity.this, "Password updated.", Toast.LENGTH_SHORT).show();
                EditSettingsActivity.this.finish();
*/


            } else {
                Toast.makeText(EditSettingsActivity.this, "New passwords don't match.", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(EditSettingsActivity.this, "Your actual password isn't correct.", Toast.LENGTH_SHORT).show();
        }



    }
}
