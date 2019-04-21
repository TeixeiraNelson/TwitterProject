package ch.ribeiropython.twitterproject.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import ch.ribeiropython.twitterproject.entity.UserEntity;

public class UserListLiveData extends LiveData<List<UserEntity>> {

    private static final String TAG = "UserListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final MyValueEventListener listener = new MyValueEventListener();

    public UserListLiveData(DatabaseReference ref, String owner) {
        reference = ref;
        this.owner = owner;
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
            setValue(toAccounts(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<UserEntity> toAccounts(DataSnapshot snapshot) {
        List<UserEntity> users = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            UserEntity entity = childSnapshot.getValue(UserEntity.class);
            entity.setIdUserEntity(childSnapshot.getKey());
            entity.setOwner(owner);
            users.add(entity);
        }
        return users;
    }

}
