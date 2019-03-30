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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import ch.ribeiropython.twitterproject.entity.TweetDatabaseDeux;

public class Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, android.view.Menu {

    private ListView listViewTweet;
    private oneTweetAdapter mAdapter;
    TweetDatabaseDeux db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialisation de la page menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LoadTweets();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Si on clique sur le bouton rond en bas ca lance l'activité qui permet d'envoyer un nouveau tweet
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

        // Récupère les extra pour savoir si il faut afficher tout les tweets ou uniquement ceux qui possède un hastags particulier
        Bundle extras = getIntent().getExtras();
        String hastagsSearch="";
        int TweetByHastags=0;

        if (extras != null) {
            hastagsSearch = extras.getString("hastagsSearch");
            TweetByHastags=1;
            Toast.makeText(Menu.this,"Search with Hastags: "+ hastagsSearch, Toast.LENGTH_SHORT).show();
        }

        //Ajoute la liste de tweet récupérer dans l'adapter
        mAdapter = new oneTweetAdapter(this,getListTweet(TweetByHastags,hastagsSearch));
        listViewTweet.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadTweets();

    }

    public ArrayList<oneTweet> getListTweet(int TweetByHastags,String hastagsSearch)
    {
        ArrayList<oneTweet> tweetsList = new ArrayList<>();
        listViewTweet = (ListView) findViewById(R.id.listViewTweet);

        //Charge les infos présente dans la base de données tweets
        db = TweetDatabaseDeux.getAppDatabase(this);
        List<oneTweet> Tweets;

        if (TweetByHastags==0)
            Tweets = db.tweetDao().getAllTweetsWithUsername();
        else
            Tweets = db.tweetDao().getAllTweetsByHastags(hastagsSearch);

        //Ajoute a la liste les tweets récupéré
        for (oneTweet tweet : Tweets){
            // Toast.makeText(Menu.this, tweet.getIdUser(), Toast.LENGTH_SHORT).show();
            System.out.println("Affiche ===> Pseudo : "+tweet.getPseudo()+" message : "+tweet.getTweet()+" hastags : "+tweet.getHashtag()+" id tweet : "+tweet.getIdTweet());
            tweetsList.add(new oneTweet(tweet.getPseudo(),tweet.getTweet() , tweet.getHashtag(), tweet.getIdTweet()));
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
