package ch.ribeiropython.twitterproject.entity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TweetEntityDeux.class,UserEntity.class}, version = 1, exportSchema = false)
public abstract class TweetDatabaseDeux extends RoomDatabase {

    private static TweetDatabaseDeux INSTANCE;

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    //Declare the two Dao
    public abstract TweetDao tweetDao();
    public abstract UserDao UserDao();

    public synchronized static TweetDatabaseDeux getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TweetDatabaseDeux.class, "tweet-database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
