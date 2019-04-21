package ch.ribeiropython.twitterproject.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserEntity {


    private String idUserEntity;
    private String email;
    private String pass;
    private String nickname;
    private String owner;

    public UserEntity(String email, String pass, String nickname) {
        this.email = email;
        this.pass = pass;
        this.nickname = nickname;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("pass", pass);
        result.put("nickname", nickname);
        return result;
    }


    @Exclude
    public String getOwner() {
        return owner;
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
    public String getIdUserEntity() {
        return idUserEntity;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
    public void setIdUserEntity(String idUserEntity) {
        this.idUserEntity = idUserEntity;
    }
}
