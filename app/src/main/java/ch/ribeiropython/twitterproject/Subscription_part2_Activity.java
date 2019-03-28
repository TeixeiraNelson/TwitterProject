package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;

public class Subscription_part2_Activity extends AppCompatActivity {

    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_part2_);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) getIntent().getSerializableExtra("User");
        }



    }

    public void verifyNickname (View view){
        EditText nicknameInput = findViewById(R.id.inscr_nickname_input);
        String nickname = nicknameInput.getText().toString();
        TextView status = findViewById(R.id.inscr_verif_state);

        if (verifyDatabaseNickname(nickname)) {
            status.setText("'"+ nickname + "'" + " is free to use !");
            status.setTextColor(Color.GREEN);

            Button btn = findViewById(R.id.inscr_btn_next2);
            btn.setEnabled(true);
            btn.setVisibility(View.VISIBLE);
            user.nickname = nickname;

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_nickname), Toast.LENGTH_SHORT ).show();

            status.setText(nickname + ": already in use.");
            status.setTextColor(Color.RED);
        }
    }

    private boolean verifyDatabaseNickname(String nickname){
        TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);

        String usersNickname = db.UserDao().getByNickname(nickname);

        if(usersNickname!=null){
            return false;
        } else {
            return true;
        }
    }

    public void launchSubPart3(View view) {

        Intent inscrPart3 = new Intent(getApplicationContext(), subscription_part3_Activity.class);
        inscrPart3.putExtra("User", user);
        startActivity(inscrPart3);
        finish();

    }
}
