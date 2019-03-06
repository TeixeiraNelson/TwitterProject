package ch.ribeiropython.twitterproject;

public class oneTweet {

    private String pseudo;
    private String tweet;
    private String hashtag;

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

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public oneTweet(String pseudo, String tweet, String hashtag) {
        this.pseudo = pseudo;
        this.tweet = tweet;
        this.hashtag = hashtag;
    }
}
