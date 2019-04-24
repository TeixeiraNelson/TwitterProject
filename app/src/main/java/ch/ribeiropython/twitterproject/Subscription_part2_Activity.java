package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        /*
        Method that verifies the nickname if it is already in use during the subscription
        it is Case Sensitive

        It checks in the database.
         */
        EditText nicknameInput = findViewById(R.id.inscr_nickname_input);
        String nickname = nicknameInput.getText().toString();


        verifyDatabaseNickname(nickname);
    }

    private void verifyDatabaseNickname(String nickname){


         /*
            This method will check in the firebase data base if the username is already in use
            in the User collection.
         */
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User").orderBy("Nickname", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int nb=0;
                        for (DocumentSnapshot doc : task.getResult())
                        {
                            System.out.println("===== "+doc.getString("Nickname"));
                            System.out.println("===== N to test : " + nickname);
                            if(nickname.equals(doc.getString("Nickname"))){
                                nb++;
                            }


                        }
                        TextView status = findViewById(R.id.inscr_verif_state);
                        if(nb==0){

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
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Subscription_part2_Activity.this,"Fail on loading database.", Toast.LENGTH_SHORT).show();
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
