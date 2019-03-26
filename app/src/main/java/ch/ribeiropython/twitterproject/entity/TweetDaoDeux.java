package ch.ribeiropython.twitterproject.entity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TweetDaoDeux {

    @Insert
    void insert(TweetEntityDeux  tweetEntity);

    @Update
    void update(TweetEntityDeux tweetEntity);

    @Delete
    void delete(TweetEntityDeux tweetEntity);

    @Query("DELETE FROM tweets")
    void deleteAllTweetEntity();

    @Query("SELECT * FROM tweets")
    List<TweetEntityDeux> getAllTweets();

    @Query("SELECT * FROM tweets WHERE idUser IN (:idUsers)")
    List<TweetEntityDeux> getAllTweetsByIds(int[] idUsers);

    @Query("SELECT * FROM tweets WHERE id LIKE :idTweets LIMIT 1")
    TweetEntityDeux findByName(int idTweets);

}
