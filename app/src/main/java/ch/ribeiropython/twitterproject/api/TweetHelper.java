package ch.ribeiropython.twitterproject.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TweetHelper {
    private static final String COLLECTION_NAME = "Tweet";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getAllTweet(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Query getAllTweetbyDate(){
        return getAllTweet()
                .orderBy("Date");
    }

    public static Task<Void> deleteTweet(String uid) {
        return TweetHelper.getAllTweet().document(uid).delete();
    }

    public static Task<Void> updateTweet(String newTweetMsg, String newHashtags, String idTweet) {
        return TweetHelper.getAllTweet().document(idTweet).update("Message",newTweetMsg,"Hastags",newHashtags);
    }

}
