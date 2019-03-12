package ch.ribeiropython.twitterproject.entity;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TweetRepository {
    private TweetDao tweetDao;
    private LiveData<List<TweetEntity>> allTweets;

    public TweetRepository(Application application){
        TweetDatabase database = TweetDatabase.getInstance(application);

        tweetDao = database.tweetDao();
        allTweets  = tweetDao.getAllTweets();
    }

    public void insert(TweetEntity tweetEntity){
        new InsertTweetAsyncTask(tweetDao).execute(tweetEntity);
    }

    public void update(TweetEntity tweetEntity){
        new UpdateTweetAsyncTask(tweetDao).execute(tweetEntity);
    }
    public void delete(TweetEntity tweetEntity){
        new DeleteTweetAsyncTask(tweetDao).execute(tweetEntity);

    }
    public void deleteAllTweets(){
        new DeleteAllTweetsAsyncTask(tweetDao).execute();
    }

    public LiveData<List<TweetEntity>> getAllTweets(){
       return allTweets;
    }

    private static class InsertTweetAsyncTask extends AsyncTask<TweetEntity, Void, Void>{

        private TweetDao tweetDao;

        private InsertTweetAsyncTask(TweetDao tweetDao){
            this.tweetDao = tweetDao;
        }

        @Override
        protected Void doInBackground(TweetEntity... tweetEntities) {

            tweetDao.insert(tweetEntities[0]);

            return null;
        }
    }
    private static class UpdateTweetAsyncTask extends AsyncTask<TweetEntity, Void, Void>{

        private TweetDao tweetDao;

        private UpdateTweetAsyncTask(TweetDao tweetDao){
            this.tweetDao = tweetDao;
        }

        @Override
        protected Void doInBackground(TweetEntity... tweetEntities) {

            tweetDao.update(tweetEntities[0]);

            return null;
        }
    }
    private static class DeleteTweetAsyncTask extends AsyncTask<TweetEntity, Void, Void>{

        private TweetDao tweetDao;

        private DeleteTweetAsyncTask(TweetDao tweetDao){
            this.tweetDao = tweetDao;
        }

        @Override
        protected Void doInBackground(TweetEntity... tweetEntities) {

            tweetDao.delete(tweetEntities[0]);

            return null;
        }
    }
    private static class DeleteAllTweetsAsyncTask extends AsyncTask<Void, Void, Void>{

        private TweetDao tweetDao;

        private DeleteAllTweetsAsyncTask(TweetDao tweetDao){
            this.tweetDao = tweetDao;
        }

        @Override
        protected Void doInBackground(Void... tweetEntities) {

            tweetDao.deleteAllTweetEntity();

            return null;
        }
    }

}
