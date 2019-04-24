package ch.ribeiropython.twitterproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import ch.ribeiropython.twitterproject.repository.UserRepository;

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
            String username;
            UserRepository.UpdateUsernameByEmail(email);
            username = UserRepository.getActualUsername();
            System.out.println("Username" + username);
            usernameTextView.setText(username);
        }
    }

    public void ChangeEmail (View o){
        /*
            Method that is called by the button to change the email in the settings activity
            The method will update the new email of the user in the database.

            it checkes also the email syntax.
         */



    }

    public void ChangePassword (View o){

        /*
            Method called by the change password button, that updates the new password of the user in the database

           The method verifies as well that both passwords match and that the actual password is correct.
         */


    }
}
