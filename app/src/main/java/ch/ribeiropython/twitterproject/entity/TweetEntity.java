package ch.ribeiropython.twitterproject.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tweets")
public class TweetEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo (name = "message")
    private String message;

    @ColumnInfo (name="idUser")
    private int idUser;

    @ColumnInfo (name = "hashtags")
    private String hashtags;

    public TweetEntity(String message, int idUser, String hashtags) {
        this.message = message;
        this.idUser = idUser;
        this.hashtags = hashtags;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getHashtags() {
        return hashtags;
    }
}
