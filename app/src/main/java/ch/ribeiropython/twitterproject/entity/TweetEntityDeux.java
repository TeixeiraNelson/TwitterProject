package ch.ribeiropython.twitterproject.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TweetEntityDeux {

    private String idTweetEntity;
    private String message;
    private String idUser;
    private String hashtags;
    private String owner;

    public TweetEntityDeux(String message, String idUser, String hashtags) {

        //Check if the tweet is shorter than 120 caractere
        if(message.length()<=120)
            this.message = message;
        else
            this.message = message.substring(0,120);

        this.idUser = idUser;
        this.hashtags = hashtags;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("idUser", idUser);
        result.put("hastags", hashtags);
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getIdUser() {
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

    public void setIdUser(String idUser) {
        this.idUser= idUser;
    }

    public void setHashtags(String hashtags) {
        this.hashtags= hashtags;
    }

    public String getIdTweetEntity() {
        return idTweetEntity;
    }

    public void setIdTweetEntity(String idTweetEntity) {
        this.idTweetEntity = idTweetEntity;
    }

    @Exclude
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) { this.owner = owner;   }

}
