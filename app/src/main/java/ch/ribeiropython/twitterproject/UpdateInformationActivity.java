package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

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


        Button passRequest = findViewById(R.id.passwd_request_btn);
        passRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail("user@example.com")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateInformationActivity.this.getApplicationContext(),"We sent an email to you with your password reset instructions !",Toast.LENGTH_LONG);
                                }
                            }
                        });

            }
        });
    }
}
