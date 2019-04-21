package ch.ribeiropython.twitterproject.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import ch.ribeiropython.twitterproject.entity.UserEntity;

public class UserLiveData extends LiveData<UserEntity> {

    private static final String TAG = "AccountLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final UserLiveData.MyValueEventListener listener = new UserLiveData.MyValueEventListener();

    public UserLiveData(DatabaseReference ref) {
        reference = ref;
        owner = ref.getParent().getParent().getKey();
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
            UserEntity entity = dataSnapshot.getValue(UserEntity.class);
            entity.setIdUserEntity(dataSnapshot.getKey());
            entity.setOwner(owner);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }


}
