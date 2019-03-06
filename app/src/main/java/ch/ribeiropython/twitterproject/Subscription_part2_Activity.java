package ch.ribeiropython.twitterproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Subscription_part2_Activity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_part2_);


        user = (User) getIntent().getParcelableExtra("User");

    }

    public void verifyNickname (View view){
        EditText nicknameInput = findViewById(R.id.inscr_nickname_input);
        String nickname = nicknameInput.getText().toString();
        TextView status = findViewById(R.id.inscr_verif_state);

        if (verifyDatabaseNickname(nickname)) {
            status.setText(nickname + " free to use ");
            status.setTextColor(Color.GREEN);

            Button btn = findViewById(R.id.inscr_btn_next2);
            btn.setEnabled(true);
            
        } else {
            Toast.makeText(this, getResources().getString(R.string.invalid_nickname), Toast.LENGTH_SHORT );

            status.setText(nickname + ": already in use.");
            status.setTextColor(Color.RED);
        }
    }

    private boolean verifyDatabaseNickname(String nickname){
        /*
        TODO : program that method.
         */
        return true;
    }
}
