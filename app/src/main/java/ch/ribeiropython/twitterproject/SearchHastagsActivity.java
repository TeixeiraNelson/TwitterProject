package ch.ribeiropython.twitterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SearchHastagsActivity extends AppCompatActivity {

    TweetDatabaseDeux db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hastags);

        Button btnSearchHastags = findViewById(R.id.btnSearchHastags);
        btnSearchHastags.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              EditText hashTags = findViewById(R.id.txtHastagsSearch);
              String hashtags = hashTags.getText().toString();
              hashtags = hashtags.replaceAll("#","");

              db = TweetDatabaseDeux.getAppDatabase(SearchHastagsActivity.this.getApplicationContext());

              Intent intent = new Intent(SearchHastagsActivity.this.getApplicationContext(), Menu.class);

              intent.putExtra("hastagsSearch", hashtags);

              startActivity(intent);
              finish();
          }
      });
    }


}
