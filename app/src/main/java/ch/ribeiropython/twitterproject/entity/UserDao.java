package ch.ribeiropython.twitterproject.entity;

import android.database.sqlite.SQLiteConstraintException;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert
    void insert(UserEntity userEntity) throws SQLiteConstraintException;

    @Insert
    void insertAll(UserEntity... users) throws SQLiteConstraintException;

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);

    @Query("DELETE FROM users")
    void deleteAllUserEntity();

    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();


}
