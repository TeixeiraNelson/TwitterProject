package ch.ribeiropython.twitterproject.firebase;

public class UserFb {

    private String Email;
    private String Nickname;

    public UserFb(String email, String nickname){
        Email = email;
        Nickname = nickname;
    }
    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
