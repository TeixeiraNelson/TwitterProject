package ch.ribeiropython.twitterproject.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey (autoGenerate = true)
    private Long id;

    @ColumnInfo (name="email")
    private String email;

    @ColumnInfo (name="pass")
    private String pass;

    @ColumnInfo (name="nickname")
    private String nickname;

}
