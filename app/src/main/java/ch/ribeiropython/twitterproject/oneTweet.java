package ch.ribeiropython.twitterproject;

public class oneTweet {

    /*
        Class for the oneTweet Adapter representing a Tweet object
     */
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

        if(tweet.length()<=120)
            this.tweet = tweet;
        else
            this.tweet = tweet.substring(0,119);
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

        if(tweet.length()<=120)
            this.tweet = tweet;
        else
            this.tweet = tweet.substring(0,119);

        this.hashtag = hashtag;
        this.idTweet = idTweet;
    }
}
