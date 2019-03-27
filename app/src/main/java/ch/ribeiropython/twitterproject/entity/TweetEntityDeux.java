package ch.ribeiropython.twitterproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tweets")
public class TweetEntityDeux {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo (name = "message")
    private String message;

    @ColumnInfo (name="idUser")
    private int idUser;

    @ColumnInfo (name = "hashtags")
    private String hashtags;

    public TweetEntityDeux(String message, int idUser, String hashtags) {

        if(message.length()<=120)
            this.message = message;
        else
            this.message = message.substring(0,120);

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

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        if(message.length()<=120)
            this.message = message;
        else
            this.message = message.substring(0,119);
    }

    public void setIdUser(int idUser) {
        this.idUser= idUser;
    }

    public void setHashtags(String hashtags) {
        this.hashtags= hashtags;
    }
}
