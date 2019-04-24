package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import ch.ribeiropython.twitterproject.repository.UserRepository;

public class EditSettingsActivity extends BaseActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private String actualUsername = "";



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
            UserRepository.getInstance().UpdateUsernameByEmail(email);

            TextView emailView = findViewById(R.id.email_txtView);
            emailView.setText(email);

            uploadUserStats(email);

            storageRef = storage.getReferenceFromUrl("gs://twitter-c9248.appspot.com/images");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("User").orderBy("Email", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            String username = "";

                            for (DocumentSnapshot doc : task.getResult())
                            {
                                System.out.println("===== Email : " + doc.getString("Email")+" - "+doc.getString("Nickname"));
                                System.out.println("===== E to test : " + email);


                                if(doc.getString("Email").equals(email)){
                                    username = doc.getString("Nickname");
                                    System.out.println("Username" + username);
                                    usernameTextView.setText(username);
                                    actualUsername = username;
                                    System.out.println("ACTUAL USERNAME " + actualUsername);
                                    loadWithGlide(username);
                                }


                            }



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });






        }
    }

    private void uploadUserStats(String emailUserConnect) {
        /*
            Uploads UI with users stats
         */

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Tweet").orderBy("Date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int nbTweets = 0;
                        int nbHashtags = 0;
                        for (DocumentSnapshot doc : task.getResult())
                        {


                            if(doc.getString("Email").equals(emailUserConnect)){
                                nbTweets++;

                                String hashtags = doc.getString("Hashtags");
                                int count = 0;

                                for(int i=0; i < hashtags.length(); i++)
                                {    if(hashtags.charAt(i) == ' ')
                                    count++;
                                }
                                nbHashtags+=count;



                            }



                        }

                        TextView nbTweetsT = findViewById(R.id.nb_tweets_uploaded);
                        nbTweetsT.setText(String.valueOf(nbTweets));

                        TextView nbHashtagsT = findViewById(R.id.nb_hashtags_used);
                        nbHashtagsT.setText(String.valueOf(String.valueOf(nbHashtags)));



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditSettingsActivity.this,"fail on loading Statistics", Toast.LENGTH_SHORT).show();
                    }
                });
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

    public void loadWithGlide(String username) {
        // [START storage_load_with_glide]
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/"+username+".jpg");

        // ImageView in your Activity
        ImageView imageView = findViewById(R.id.profile_img);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        storageRef.child(username+".jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image

                Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }



    public void updateImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageREturnedIntent){
        super.onActivityResult(requestCode, resultCode, imageREturnedIntent);
        ImageView img = findViewById(R.id.profile_img);

        System.out.println("IM HERE : + " + requestCode);
        switch (requestCode){
            case 0:
                Uri selectedImage = imageREturnedIntent.getData();
                img.setImageURI(selectedImage);
                uploadImageToFirebase();
                break;
            case 1:
                Uri selectedImage2 = imageREturnedIntent.getData();
                img.setImageURI(selectedImage2);
                uploadImageToFirebase();
                break;
        }
    }

    private void uploadImageToFirebase() {

        ImageView img = findViewById(R.id.profile_img);

        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap = img.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();
        System.out.println("UPLOAD : ACTUAL USERNAME " + actualUsername);
        StorageReference mountainsRef = storageRef.child(actualUsername+".jpg");
        UploadTask uploadTask = mountainsRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Image upload error");
                Toast.makeText(EditSettingsActivity.this.getApplicationContext(),"Image upload error",Toast.LENGTH_LONG);

            }
        });

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Image uploaded successfully");
                Toast.makeText(EditSettingsActivity.this.getApplicationContext(),"Image successfully uploaded",Toast.LENGTH_LONG);
            }
        });

    }


}
