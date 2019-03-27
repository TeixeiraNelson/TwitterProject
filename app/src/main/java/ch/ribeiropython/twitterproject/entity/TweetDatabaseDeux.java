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

    public abstract TweetDao tweetDao();
    public abstract UserDao UserDao();

    public synchronized static TweetDatabaseDeux getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TweetDatabaseDeux.class, "tweet-database")
                            /*
                            allow queries on the main thread.
                            Don't do this in a real app!
                            See PersistenceBasicSample
                            https://github.com/googlesamples/android-architecture-components/tree/master/BasicSample
                            for an example.

                            Would throw java.lang.IllegalStateException:
                            Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                            */
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
