package ch.ribeiropython.twitterproject;

import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ListView;

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
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;
import ch.ribeiropython.twitterproject.entity.UserEntity;

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

        ArrayList<oneTweet> tweetsList = new ArrayList<>();

        //Récupère les tweets et les affiches dans le listview
        listViewTweet = (ListView) findViewById(R.id.listViewTweet);
       // tweetsList.add(new oneTweet("Test pseudo 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat." , "#2013 #mateub"));
        // tweetsList.add(new oneTweet("Gafundi", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat." , "#salouti #heyheyhey"));
        /* tweetsList.add(new oneTweet("nolsen", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat." , "#jeviensjamaisencours #acausedemamaindroite"));
         */

       // TODO : Faire que sa le fasse  qu'au premier démarrage

       addTweetOndb();


        /*ArrayList<TweetEntityDeux> listTweet = new ArrayList<>();
        listTweet.add(new TweetEntityDeux("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat.", 1 , "#2013 #mateub"));
        listTweet.add(new TweetEntityDeux("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat.", 2 , "#2013 #mateub"));
        listTweet.add(new TweetEntityDeux("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat.", 31 , "#2013 #mateub"));


        db = TweetDatabaseDeux.getAppDatabase(this);

        boolean duplicates = false;

        for (TweetEntityDeux tweet : listTweet) {
            try {
               // db.tweetDao().insert(tweet);
                //db.tweetDao().insertAll(new TweetEntityDeux(tweet.getMessage(),tweet.getIdUser(),tweet.getHashtags()));
            } catch (SQLiteConstraintException e) {
                duplicates = true;
            }
        }*/

       // TweetDatabaseDeux db = TweetDatabaseDeux.getAppDatabase(this);

        //Charge les infos présente dans la base de données tweets
        db = TweetDatabaseDeux.getAppDatabase(this);
        List<oneTweet> fruits = db.tweetDao().getAllTweetsWithUsername();

        for (oneTweet fruit : fruits){
          // Toast.makeText(Menu.this, fruit.getIdUser(), Toast.LENGTH_SHORT).show();
            System.out.println("Affiche ===> Pseudo : "+fruit.getPseudo()+" message : "+fruit.getTweet()+" hastags : "+fruit.getHashtag()+" id tweet : "+fruit.getIdTweet());
            tweetsList.add(new oneTweet(fruit.getPseudo(),fruit.getTweet() , fruit.getHashtag(), fruit.getIdTweet()));
        }

        //Charge les infos présente dans la base de données users
       /* db2 = UserDatabase.getAppDatabase(this);
        List<UserEntity> users = db2.UserDao().getAllUsers();

        for (UserEntity fruit : users){
            // Toast.makeText(Menu.this, fruit.getIdUser(), Toast.LENGTH_SHORT).show();
            System.out.println("id user : "+fruit.getId()+"nickname : "+fruit.getNickname());
        }*/

        //Ajoute la liste de tweet récupérer dans l'adapter
        mAdapter = new oneTweetAdapter(this,tweetsList);
        listViewTweet.setAdapter(mAdapter);



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

    public void addTweetOndb(){

        db = TweetDatabaseDeux.getAppDatabase(this);
        boolean duplicates = false;

        ArrayList<UserEntity> listUser = new ArrayList<>();
        listUser.add(new UserEntity("bonjour@test.com","test","Test1"));
        listUser.add(new UserEntity("bonjour@test2.com","test2","Test2"));
        listUser.add(new UserEntity("bonjour@test3.com","test3","Test3"));

        for (UserEntity user : listUser) {
            try {
                db.UserDao().insert(new UserEntity(user.getEmail(),user.getPass(),user.getNickname()));
                //db.tweetDao().insertAll(new TweetEntityDeux(tweet.getMessage(),tweet.getIdUser(),tweet.getHashtags()));
            } catch (SQLiteConstraintException e) {
                duplicates = true;
            }
        }


        ArrayList<TweetEntityDeux> listTweet = new ArrayList<>();
        listTweet.add(new TweetEntityDeux("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volutpat.", 1 , "#2013 #mateub"));
        listTweet.add(new TweetEntityDeux("Hey ho ho hey", 2 , "#super"));
        listTweet.add(new TweetEntityDeux("Super nouveau tweet a moi heyheyhey", 3 , "#heyben"));
        listTweet.add(new TweetEntityDeux("je viens de décrouvrir un nouveau réseau social !", 2 , "#2emeTweet #Inshalla"));


        for (TweetEntityDeux tweet : listTweet) {
            try {
                db.tweetDao().insert(tweet);
                //db.tweetDao().insertAll(new TweetEntityDeux(tweet.getMessage(),tweet.getIdUser(),tweet.getHashtags()));
                System.out.println("Ce qu'il entre ===> Pseudo : "+tweet.getIdUser()+" message : "+tweet.getMessage()+" hastags : "+tweet.getHashtags()+" id tweet : "+tweet.getIdTweetEntity());

            } catch (SQLiteConstraintException e) {
                duplicates = true;
            }
        }



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
            //envoyé vers nouveau hashtag

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
