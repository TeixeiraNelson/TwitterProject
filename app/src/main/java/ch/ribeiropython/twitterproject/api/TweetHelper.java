package ch.ribeiropython.twitterproject.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TweetHelper {
    private static final String COLLECTION_NAME = "Tweet";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getAllTweet(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }
}
