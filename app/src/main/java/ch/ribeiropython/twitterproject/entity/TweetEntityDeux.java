package ch.ribeiropython.twitterproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tweets",foreignKeys = @ForeignKey(entity = UserEntity.class, parentColumns = "idUserEntity", childColumns = "idUser"),indices = {@Index("idUser")})
public class TweetEntityDeux {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "idTweetEntity")
    private int idTweetEntity;

    @ColumnInfo (name = "message")
    private String message;

    @ColumnInfo (name="idUser")
    private int idUser;

    @ColumnInfo (name = "hashtags")
    private String hashtags;

    public TweetEntityDeux(String message, int idUser, String hashtags) {

        //Check if the tweet is shorter than 120 caractere
        if(message.length()<=120)
            this.message = message;
        else
            this.message = message.substring(0,120);

        this.idUser = idUser;
        this.hashtags = hashtags;
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

    public void setMessage(String message) {
        //Check if the tweet is shorter than 120 caractere
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

    public int getIdTweetEntity() {
        return idTweetEntity;
    }

    public void setIdTweetEntity(int idTweetEntity) {
        this.idTweetEntity = idTweetEntity;
    }
}
