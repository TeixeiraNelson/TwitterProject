package ch.ribeiropython.twitterproject.entity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TweetViewModel extends AndroidViewModel {

    private TweetRepository repository;
    private LiveData<List<TweetEntity>> allTweets;

    public TweetViewModel(@NonNull Application application) {
        super(application);

        repository = new TweetRepository(application);
        allTweets = repository.getAllTweets();
    }

    public void insert(TweetEntity tweetEntity) {
        repository.insert(tweetEntity);
    }

    public void update(TweetEntity tweetEntity) {
        repository.update(tweetEntity);
    }

    public void delete(TweetEntity tweetEntity) {
        repository.delete(tweetEntity);
    }

    public void deleteAllTweets() {
        repository.deleteAllTweets();
    }

    public LiveData<List<TweetEntity>> getAllTweets(){
        return allTweets;
    }


}
