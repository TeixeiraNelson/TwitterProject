package ch.ribeiropython.twitterproject.entity;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

public abstract class TweetDatabase extends RoomDatabase {

    private static TweetDatabase instance;

    public abstract TweetDao tweetDao();

    public static synchronized TweetDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),TweetDatabase.class,"tweet_database").fallbackToDestructiveMigration().addCallback(callback).build();
        }
        return instance;

    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){

        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private TweetDao tweetDao;

        private PopulateDbAsyncTask(TweetDatabase db){
            tweetDao = db.tweetDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tweetDao.insert(new TweetEntity("Hello tout le monde c'est mon 1er tweets !",1,"#first #Heyheyhey"));
            tweetDao.insert(new TweetEntity("2eme tweet du tweet games ! heeeeeeeeelloo o o o",2,"#seconde #test"));
            tweetDao.insert(new TweetEntity("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet quam nec felis tempor tempor eget congue risus. Suspendisse ac ornare metus, vel volut",3,"#3eme"));
            return null;
        }
    }
}
