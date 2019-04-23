package ch.ribeiropython.twitterproject.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TweetHelper {
    private static final String COLLECTION_NAME = "Tweet";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getAllTweet(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }
    public static Query getAllMessageForChat(String chat){
        return getAllTweet()
                .document(chat)
                .collection(COLLECTION_NAME)
                .orderBy("dateCreated")
                .limit(50);
    }

}
