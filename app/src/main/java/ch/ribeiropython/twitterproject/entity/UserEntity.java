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

    public UserEntity(String email, String pass, String nickname) {
        this.email = email;
        this.pass = pass;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getNickname() {
        return nickname;
    }
}
