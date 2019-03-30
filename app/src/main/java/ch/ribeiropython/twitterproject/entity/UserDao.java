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

    @Query("SELECT email FROM users WHERE email = :email")
    String getByEmail(String email);

    @Query("SELECT nickname FROM users WHERE nickname = :nickname")
    String getByNickname (String nickname);

    @Query("SELECT * FROM users WHERE email = :email AND pass = :password")
    UserEntity getUserLogin (String email, String password);

    @Query("SELECT idUserEntity FROM users WHERE nickname = :nickname")
    int getUserId (String nickname);

    @Query("SELECT count(*) FROM users WHERE email = :email")
    int nbEmail (String email);

    @Query("UPDATE users SET email = :email WHERE idUserEntity = :iduser")
    void updateEmail(String email, int iduser);

    @Query("UPDATE users SET pass = :pass WHERE idUserEntity = :userId")
    void updatePassword(int userId, String pass);
}