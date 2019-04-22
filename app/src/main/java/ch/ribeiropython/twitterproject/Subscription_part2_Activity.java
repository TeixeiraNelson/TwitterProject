package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class Subscription_part2_Activity extends AppCompatActivity {

    private User user = new User();
    private static boolean NicknameIsFree = false;

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
        /*
        Method that verifies the nickname if it is already in use during the subscription
        it is Case Sensitive

        It checks in the database.
         */
        EditText nicknameInput = findViewById(R.id.inscr_nickname_input);
        String nickname = nicknameInput.getText().toString();
        TextView status = findViewById(R.id.inscr_verif_state);


        verifyDatabaseNickname(nickname);
        if (NicknameIsFree) {
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

    private void verifyDatabaseNickname(String nickname){
        /*
            This method will check in the firebase data base if the username is already in use
            in the User collection.
         */
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("User").orderByChild("Nickname").equalTo(nickname);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    NicknameIsFree = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                NicknameIsFree = true;
            }
        });
    }

    public void launchSubPart3(View view) {
        /*
        Method to launch next activity of the subscription
         */
        Intent inscrPart3 = new Intent(getApplicationContext(), subscription_part3_Activity.class);
        inscrPart3.putExtra("User", user);
        startActivity(inscrPart3);
        finish();

    }
}
