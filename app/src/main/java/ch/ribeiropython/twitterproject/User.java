package ch.ribeiropython.twitterproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;

public class User implements Serializable {
    public String email;
    public String password;
    public String nickname;

    public static User getUserSession(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getResources().getString(R.string.sharedPref), Context.MODE_PRIVATE);

        // add the user on the sharedPreferences so we can know which user is connected on the application
        String userString = sharedPreferences.getString("user",null);

        //Change the object to a string so we can stock in the sharedPreferences
        Gson gson = new Gson();
        User user = gson.fromJson(userString, User.class);
        return user;
    }
}

