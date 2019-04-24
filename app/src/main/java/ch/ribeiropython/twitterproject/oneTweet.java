package ch.ribeiropython.twitterproject;

public class oneTweet {

    /*
        Class for the oneTweet Adapter representing a Tweet object
     */
    private String pseudo;
    private String tweet;
    private String hashtag;
    private String idTweet;
    private boolean isMyTweet;

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

    public void setIdTweet(String idTweet) {  this.idTweet = idTweet; }

    public boolean getIsMyTweet() {  return isMyTweet;   }

    public void setIsMyTweet(boolean isMyTweet) {  this.isMyTweet = isMyTweet;  }

    public String getHashtag() {
        return hashtag;
    }

    public String getIdTweet() { return idTweet; }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public oneTweet(String pseudo, String tweet, String hashtag, String idTweet, boolean isMyTweet) {
        this.pseudo = pseudo;

        if(tweet.length()<=120)
            this.tweet = tweet;
        else
            this.tweet = tweet.substring(0,119);

        this.hashtag = hashtag;
        this.idTweet = idTweet;
        this.isMyTweet=isMyTweet;
    }
}
