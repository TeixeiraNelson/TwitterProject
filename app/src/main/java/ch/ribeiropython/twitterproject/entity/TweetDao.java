package ch.ribeiropython.twitterproject.entity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
