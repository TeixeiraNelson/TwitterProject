package ch.ribeiropython.twitterproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;

public class EditSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings);
    }

    public void ChangeEmail (View o){
        EditText email = findViewById(R.id.txtEmail);

        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
        User user = User.getUserSession(this.getApplicationContext());

        int userId = db.UserDao().getUserId(user.nickname);
        db.UserDao().updateEmail(email.getText().toString(), userId);
        Toast.makeText(EditSettingsActivity.this, "Email updated.", Toast.LENGTH_SHORT).show();
        EditSettingsActivity.this.finish();
    }

    public void ChangePassword (View o){
        EditText actualPassword = findViewById(R.id.txtOldPassword);
        EditText Password1 = findViewById(R.id.txtNewPassword);
        EditText Password2 = findViewById(R.id.txtNewPassword2);

        User user = User.getUserSession(this.getApplicationContext());
        if(actualPassword.getText().toString().equals(user.password)){
            if(Password1.getText().toString().equals(Password2.getText().toString())){
                TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);
                int userId = db.UserDao().getUserId(user.nickname);
                db.UserDao().updatePassword(userId, Password1.getText().toString());
                Toast.makeText(EditSettingsActivity.this, "Password updated.", Toast.LENGTH_SHORT).show();
                EditSettingsActivity.this.finish();



            } else {
                Toast.makeText(EditSettingsActivity.this, "New passwords don't match.", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(EditSettingsActivity.this, "Your actual password isn't correct.", Toast.LENGTH_SHORT).show();
        }



    }
}
