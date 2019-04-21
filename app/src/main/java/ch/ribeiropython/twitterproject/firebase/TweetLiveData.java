package ch.ribeiropython.twitterproject.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import ch.ribeiropython.twitterproject.entity.TweetEntityDeux;

public class TweetLiveData extends LiveData<TweetEntityDeux> {
    private static final String TAG = "TweetLiveData";

    private final DatabaseReference reference;
    private final TweetLiveData.MyValueEventListener listener = new TweetLiveData.MyValueEventListener();

    public TweetLiveData(DatabaseReference ref) {
        this.reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            TweetEntityDeux entity = dataSnapshot.getValue(TweetEntityDeux.class);
            entity.setIdTweetEntity(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
