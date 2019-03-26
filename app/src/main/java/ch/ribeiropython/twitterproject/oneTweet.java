package ch.ribeiropython.twitterproject;

public class oneTweet {

    private String pseudo;
    private String tweet;
    private String hashtag;
    private int idTweet;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getHashtag() {
        return hashtag;
    }

    public int getIdTweet() { return idTweet; }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public oneTweet(String pseudo, String tweet, String hashtag, int idTweet) {
        this.pseudo = pseudo;
        this.tweet = tweet;
        this.hashtag = hashtag;
        this.idTweet = idTweet;
    }
}
