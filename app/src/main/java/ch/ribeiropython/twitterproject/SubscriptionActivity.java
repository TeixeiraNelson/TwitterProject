package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SubscriptionActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseFirestore db;

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
        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("User").orderBy("Email", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int nb=0;
                        for (DocumentSnapshot doc : task.getResult())
                        {
                            System.out.println("===== Email : " + doc.getString("Email")+" - "+doc.getString("Nickname"));
                            System.out.println("===== E to test : " + userEmail);
                            if(userEmail.equals(doc.getString("Email"))){
                                nb++;
                            }


                        }

                        if(nb==0){
                            if(verifyEmailSyntax(userEmail)){
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
                        } else {
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT ).show();

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SubscriptionActivity.this,"Fail on loading database.", Toast.LENGTH_SHORT).show();
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
