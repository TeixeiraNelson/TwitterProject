package ch.ribeiropython.twitterproject;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, android.view.Menu {

    private ListView listViewTweet;
    private oneTweetAdapter mAdapter;
    /* TweetDatabaseDeux db; */
    FirebaseFirestore db;
    ArrayList<oneTweet> tweetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initializing menu page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Loading tweets
        db = FirebaseFirestore.getInstance();
        LoadTweets();
        /*db = FirebaseFirestore.getInstance();

        Map<String, Object> newTweet = new HashMap<>();
        newTweet.put("Message","Ecrire depuis android");
        newTweet.put("Hastags","#testdepuisandroid");
        newTweet.put("idUser_tweet","zgeg");
        newTweet.put("timestamp", ServerValue.TIMESTAMP);

        db.collection("Tweet").document()
                .set(newTweet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Menu.this,"new tweet add", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Menu.this,"fail new tweet add", Toast.LENGTH_SHORT).show();

                    }
                });*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Action to start activity to send a new tweet
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMyAccount = new Intent(view.getContext(), Tweet.class);
                startActivity(intentMyAccount);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void LoadTweets() {

        // getting extras to know if it has to print all tweets or just those that have a particular hashtag.
        /*Bundle extras = getIntent().getExtras();
        String hastagsSearch="";
        int TweetByHastags=0;

        if (extras != null) {
            hastagsSearch = extras.getString("hastagsSearch");
            TweetByHastags=1;
            Toast.makeText(Menu.this,"Search with Hastags: "+ hastagsSearch, Toast.LENGTH_SHORT).show();
        }

        //adds tweet list to the adapter
        mAdapter = new oneTweetAdapter(this,getListTweet(TweetByHastags,hastagsSearch));
        listViewTweet.setAdapter(mAdapter);*/

        tweetsList = new ArrayList<>();
        listViewTweet = (ListView) findViewById(R.id.listViewTweet);

        db.collection("Tweet").orderBy("Date")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult())
                        {
                            tweetsList.add(new oneTweet(doc.getString("Email"),doc.getString("Message") , doc.getString("Hastags")));
                            System.out.println("===== Email"+doc.getString("Email")+"  "+doc.getString("Message")+"  "+doc.getString("Hastags"));
                        }
                        mAdapter = new oneTweetAdapter(Menu.this,tweetsList);
                        listViewTweet.setAdapter(mAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Menu.this,"fail refresh list of tweet", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    // method to refresh the activity
    @Override
    protected void onResume() {
        super.onResume();

       // LoadTweets();

    }


    /*
    Method to get a tweet list by hashtag
     */
    public ArrayList<oneTweet> getListTweet(int TweetByHastags,String hastagsSearch)
    {
        ArrayList<oneTweet> tweetsList = new ArrayList<>();
        listViewTweet = (ListView) findViewById(R.id.listViewTweet);


        //Charge les infos présente dans la base de données tweets
        /* db = TweetDatabaseDeux.getAppDatabase(this); */
        List<oneTweet> Tweets = new ArrayList<>();

        if (TweetByHastags==0){

        }
          /*  Tweets = db.tweetDao().getAllTweetsWithUsername(); */
        else
           /* Tweets = db.tweetDao().getAllTweetsByHastags(hastagsSearch); */

        //Ajoute a la liste les tweets récupéré
        for (oneTweet tweet : Tweets){
            // Toast.makeText(Menu.this, tweet.getIdUser(), Toast.LENGTH_SHORT).show();
            System.out.println("Affiche ===> Pseudo : "+tweet.getPseudo()+" message : "+tweet.getTweet()+" hastags : "+tweet.getHashtag()+" id tweet : "+tweet.getIdTweet());
            //tweetsList.add(new oneTweet(tweet.getPseudo(),tweet.getTweet() , tweet.getHashtag(), tweet.getIdTweet()));
        }

        return tweetsList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_tweet) {
            Intent intent = new Intent(getApplicationContext(), Tweet.class);
            startActivity(intent);
        } else if (id == R.id.nav_hashtag) {
            Intent intent = new Intent(getApplicationContext(), SearchHastagsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(getApplicationContext(), EditSettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logoff){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            FirebaseAuth.getInstance().signOut();
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public MenuItem add(CharSequence title) {
        return null;
    }

    @Override
    public MenuItem add(int titleRes) {
        return null;
    }

    @Override
    public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
        return null;
    }

    @Override
    public MenuItem add(int groupId, int itemId, int order, int titleRes) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(CharSequence title) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(int titleRes) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
        return null;
    }

    @Override
    public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
        return 0;
    }

    @Override
    public void removeItem(int id) {

    }

    @Override
    public void removeGroup(int groupId) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {

    }

    @Override
    public void setGroupVisible(int group, boolean visible) {

    }

    @Override
    public void setGroupEnabled(int group, boolean enabled) {

    }

    @Override
    public boolean hasVisibleItems() {
        return false;
    }

    @Override
    public MenuItem findItem(int id) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public MenuItem getItem(int index) {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
        return false;
    }

    @Override
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean performIdentifierAction(int id, int flags) {
        return false;
    }

    @Override
    public void setQwertyMode(boolean isQwerty) {

    }
}
