package ch.ribeiropython.twitterproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
