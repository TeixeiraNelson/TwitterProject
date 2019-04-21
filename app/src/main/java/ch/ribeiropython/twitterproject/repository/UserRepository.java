package ch.ribeiropython.twitterproject.repository;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import ch.ribeiropython.twitterproject.entity.UserEntity;
import ch.ribeiropython.twitterproject.firebase.UserListLiveData;
import ch.ribeiropython.twitterproject.firebase.UserLiveData;
import ch.ribeiropython.twitterproject.util.OnAsyncEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserRepository {

    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public void register(final UserEntity user, final OnAsyncEventListener callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPass()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.setIdUserEntity(FirebaseAuth.getInstance().getCurrentUser().getUid());
                insert(user, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }

    public LiveData<UserEntity> getAccount(final String accountId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("accounts")
                .child(accountId);
        return new UserLiveData(reference);
    }

    public LiveData<List<UserEntity>> getByOwner(final String owner) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(owner)
                .child("accounts");
        return new UserListLiveData(reference, owner);
    }

    public void insert(final UserEntity account, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(account.getOwner())
                .child("accounts");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(account.getOwner())
                .child("accounts")
                .child(key)
                .setValue(account, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void update(final UserEntity user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("tweets")
                .child(user.getIdUserEntity())
                .updateChildren(user.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(user.getPass())
                .addOnFailureListener(
                        e -> Log.d(TAG, "updateUser failure!", e)
                );
    }

    public void delete(final UserEntity account, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(account.getOwner())
                .child("accounts")
                .child(account.getIdUserEntity())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void transaction(final UserEntity sender, final UserEntity recipient,
                            OnAsyncEventListener callback) {
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                rootReference
                        .child("users")
                        .child(sender.getOwner())
                        .child("accounts")
                        .child(sender.getIdUserEntity())
                        .updateChildren(sender.toMap());

                rootReference
                        .child("users")
                        .child(recipient.getOwner())
                        .child("accounts")
                        .child(recipient.getIdUserEntity())
                        .updateChildren(recipient.toMap());

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    callback.onFailure(databaseError.toException());
                } else {
                    callback.onSuccess();
                }
            }
        });
    }

}
