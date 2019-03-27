package ch.ribeiropython.twitterproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "idUserEntity")
    private int idUserEntity;

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


    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getNickname() {
        return nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIdUserEntity() {
        return idUserEntity;
    }

    public void setIdUserEntity(int idUserEntity) {
        this.idUserEntity = idUserEntity;
    }
}
