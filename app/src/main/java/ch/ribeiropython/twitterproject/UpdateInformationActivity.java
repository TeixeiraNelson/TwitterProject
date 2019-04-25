package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);

        Intent intent = getIntent();

        String email = intent.getStringExtra("Email");
        String nickname = intent.getStringExtra("Nickname");

        EditText emailEdit = findViewById(R.id.email_editText);
        emailEdit.setHint(email);

        EditText nicknameEdit = findViewById(R.id.username_editText2);
        nicknameEdit.setHint(nickname);

        EditText passwordEdit = findViewById(R.id.password_editText2);


        Button passRequest = findViewById(R.id.passwd_request_btn);
        passRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateInformationActivity.this.getApplicationContext(),"We sent an email to you with your password reset instructions !",Toast.LENGTH_LONG).show();

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("Sucessfully sent email");
                                }
                            }
                        });

            }
        });


        Button updateRequest = findViewById(R.id.change_settings_btn);
        updateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                String emailInput = emailEdit.getText().toString();
                String nicknameInput = nicknameEdit.getHint().toString();
                String passwordInput = passwordEdit.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // Get auth credentials from the user for re-authentication
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email, passwordInput); // Current Login Credentials \\
                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                //Now change your email address \\
                                //----------------Code for Changing Email Address----------\\
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateEmail(emailInput)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseFirestore db;
                                                    db = FirebaseFirestore.getInstance();

                                                    Map<String, Object> newUser = new HashMap<>();
                                                    newUser.put("Email", emailInput);
                                                    newUser.put("Nickname", nicknameInput);





                                                    db.collection("User").orderBy("Email",Query.Direction.DESCENDING)
                                                            .get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task< QuerySnapshot > task) {



                                                                    for (DocumentSnapshot doc : task.getResult())
                                                                    {



                                                                        if(doc.getString("Email").equals(emailEdit.getHint().toString())){

                                                                            doc.getReference().delete();
                                                                            System.out.println("User deleted from db from email update !!!");

                                                                            db.collection("User").document()
                                                                                    .set(newUser)
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {
                                                                                            System.out.println("User added to db from email update !!!");
                                                                                            finish();

                                                                                        }
                                                                                    })
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            System.out.println("Adding user impossible.");

                                                                                        }
                                                                                    });

                                                                            return;
                                                                        }


                                                                    }



                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {

                                                                }
                                                            });








                                                    Toast.makeText(UpdateInformationActivity.this.getApplicationContext(),"Your Email has been updated.",Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });
                                //----------------------------------------------------------\\
                            }
                        });



            }
        });


        /*
        Button changeUsername = findViewById(R.id.change_settings_btn2);
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = emailEdit.getHint().toString();
                String nicknameInput = nicknameEdit.getText().toString();


                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();

                Map<String, Object> newUser = new HashMap<>();
                newUser.put("Email", emailInput);
                newUser.put("Nickname", nicknameInput);





                db.collection("User").orderBy("Email",Query.Direction.DESCENDING)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task< QuerySnapshot > task) {



                                for (DocumentSnapshot doc : task.getResult())
                                {



                                    if(doc.getString("Nickname").equals(nicknameEdit.getHint().toString())){

                                        doc.getReference().delete();

                                        db.collection("User").document()
                                                .set(newUser)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        System.out.println("User added to db.");
                                                        finish();



                                                        return;

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        System.out.println("Adding user impossible.");

                                                    }
                                                });
                                        return;
                                    }


                                }



                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });








                Toast.makeText(UpdateInformationActivity.this.getApplicationContext(),"Your Nickname has been updated.",Toast.LENGTH_LONG).show();

            }
        }); */

    }
}
