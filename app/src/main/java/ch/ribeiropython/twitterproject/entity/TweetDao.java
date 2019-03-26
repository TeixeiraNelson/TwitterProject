package ch.ribeiropython.twitterproject.entity;

import android.database.sqlite.SQLiteConstraintException;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TweetDao {

    @Insert
    void insert(TweetEntityDeux tweetEntity) throws SQLiteConstraintException;

    @Insert
    void insertAll(TweetEntityDeux... Tweets) throws SQLiteConstraintException;

    @Update
    void update(TweetEntityDeux tweetEntity);

    @Delete
    void delete(TweetEntityDeux tweetEntity);

    @Query("DELETE FROM tweets")
    void deleteAllTweetEntity();

    @Query("SELECT * FROM tweets")
    List<TweetEntityDeux> getAllTweets();

}
