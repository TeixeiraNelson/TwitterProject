package ch.ribeiropython.twitterproject.entity;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import androidx.lifecycle.LiveData;

@Dao
public interface TweetDao {

    @Insert
    void insert(TweetEntity tweetEntity);

    @Update
    void update(TweetEntity tweetEntity);

    @Delete
    void delete(TweetEntity tweetEntity);

    @Query("DELETE FROM tweets")
    void deleteAllTweetEntity();

    @Query("SELECT * FROM tweets")
    LiveData<List<TweetEntity>> getAllTweets();

}
