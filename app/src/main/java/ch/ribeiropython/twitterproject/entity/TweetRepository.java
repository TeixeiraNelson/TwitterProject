package ch.ribeiropython.twitterproject.entity;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class TweetRepository {
    private TweetDao tweetDao;
    private List<TweetEntityDeux> allTweets;

    public TweetRepository(Application application){
        TweetDatabaseDeux database = TweetDatabaseDeux.getAppDatabase(application);

        tweetDao = database.tweetDao();
        allTweets  = tweetDao.getAllTweets();
    }

    public void insert(TweetEntityDeux tweetEntity){
        new InsertTweetAsyncTask(tweetDao).execute(tweetEntity);
    }

    public void update(TweetEntityDeux tweetEntity){
        new UpdateTweetAsyncTask(tweetDao).execute(tweetEntity);
    }
    public void delete(TweetEntityDeux tweetEntity){
        new DeleteTweetAsyncTask(tweetDao).execute(tweetEntity);

    }
    public void deleteAllTweets(){
        new DeleteAllTweetsAsyncTask(tweetDao).execute();
    }

    public List<TweetEntityDeux> getAllTweets(){
       return allTweets;
    }

    private static class InsertTweetAsyncTask extends AsyncTask<TweetEntityDeux, Void, Void>{

        private TweetDao tweetDao;

        private InsertTweetAsyncTask(TweetDao tweetDao){
            this.tweetDao = tweetDao;
        }

        @Override
        protected Void doInBackground(TweetEntityDeux... tweetEntities) {

            tweetDao.insert(tweetEntities[0]);

            return null;
        }
    }
    private static class UpdateTweetAsyncTask extends AsyncTask<TweetEntityDeux, Void, Void>{

        private TweetDao tweetDao;

        private UpdateTweetAsyncTask(TweetDao tweetDao){
            this.tweetDao = tweetDao;
        }

        @Override
        protected Void doInBackground(TweetEntityDeux... tweetEntities) {

            tweetDao.update(tweetEntities[0]);

            return null;
        }
    }
    private static class DeleteTweetAsyncTask extends AsyncTask<TweetEntityDeux, Void, Void>{

        private TweetDao tweetDao;

        private DeleteTweetAsyncTask(TweetDao tweetDao){
            this.tweetDao = tweetDao;
        }

        @Override
        protected Void doInBackground(TweetEntityDeux... tweetEntities) {

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
