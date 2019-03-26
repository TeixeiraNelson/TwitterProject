package ch.ribeiropython.twitterproject.entity;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TweetViewModel extends AndroidViewModel {

    private TweetRepository repository;
    private List<TweetEntityDeux> allTweets;

    public TweetViewModel(@NonNull Application application) {
        super(application);

        repository = new TweetRepository(application);
        allTweets = repository.getAllTweets();
    }

    public void insert(TweetEntityDeux tweetEntity) {
        repository.insert(tweetEntity);
    }

    public void update(TweetEntityDeux tweetEntity) {
        repository.update(tweetEntity);
    }

    public void delete(TweetEntityDeux tweetEntity) {
        repository.delete(tweetEntity);
    }

    public void deleteAllTweets() {
        repository.deleteAllTweets();
    }

    public List<TweetEntityDeux> getAllTweets(){
        return allTweets;
    }


}
