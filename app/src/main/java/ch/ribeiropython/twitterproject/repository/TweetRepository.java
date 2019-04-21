package ch.ribeiropython.twitterproject.repository;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.LiveData;
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;
import ch.ribeiropython.twitterproject.firebase.TweetLiveData;
import ch.ribeiropython.twitterproject.util.OnAsyncEventListener;

public class TweetRepository {

    private static final String TAG = "TweetRepository";

    private static TweetRepository instance;

    private TweetRepository() {
    }

    public static TweetRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new TweetRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<TweetEntityDeux> getTweet(final String clientId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Tweet")
                .child(clientId);
        return new TweetLiveData(reference);
    }


    private void insert(final TweetEntityDeux tweet, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("Tweet")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(tweet, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onFailure(null);
                                        Log.d(TAG, "Rollback successful: tweet deleted");
                                    } else {
                                        callback.onFailure(task.getException());
                                        Log.d(TAG, "Rollback failed: tweets:failure",
                                                task.getException());
                                    }
                                });
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final TweetEntityDeux tweet, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("Tweet")
                .child(tweet.getOwner())
                .updateChildren(tweet.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final TweetEntityDeux client, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("Tweet")
                .child(client.getIdTweetEntity())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
