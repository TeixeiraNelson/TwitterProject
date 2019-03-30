package ch.ribeiropython.twitterproject.entity;

import android.database.sqlite.SQLiteConstraintException;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import ch.ribeiropython.twitterproject.oneTweet;

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

    @Query("DELETE FROM tweets WHERE idTweetEntity = :idTweet")
    void deleteTweet(int idTweet);

    @Query("SELECT * FROM tweets")
    List<TweetEntityDeux> getAllTweets();

    @Query("SELECT u.nickname as pseudo,t.message as tweet,t.hashtags as hashtag, t.idTweetEntity as idTweet FROM tweets t,users u WHERE t.idUser = u.idUserEntity AND instr(t.hashtags,:hastags) > 1  ORDER BY t.idTweetEntity DESC")
    List<oneTweet> getAllTweetsByHastags(String hastags);

    @Query("SELECT u.nickname as pseudo,t.message as tweet,t.hashtags as hashtag, t.idTweetEntity as idTweet FROM tweets t,users u WHERE t.idUser = u.idUserEntity ORDER BY t.idTweetEntity DESC")
    List<oneTweet> getAllTweetsWithUsername();

}
