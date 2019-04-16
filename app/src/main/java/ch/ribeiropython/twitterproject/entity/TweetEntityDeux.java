package ch.ribeiropython.twitterproject.entity;

import java.util.HashMap;
import java.util.Map;

public class TweetEntityDeux {

    private int idTweetEntity;

    private String message;

    private int idUser;

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
