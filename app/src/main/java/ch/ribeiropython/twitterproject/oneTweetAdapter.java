package ch.ribeiropython.twitterproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class oneTweetAdapter extends ArrayAdapter<oneTweet> {

    private Context mContext;
    private List<oneTweet> tweetList = new ArrayList<>();

    public oneTweetAdapter(@NonNull Context context, ArrayList<oneTweet> list) {
        super(context, 0 , list);
        mContext = context;
        tweetList = list;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.tweet,parent,false);

        final oneTweet currentMovie = tweetList.get(position);

        TextView pseudo = (TextView) listItem.findViewById(R.id.textView_pseudo);
        pseudo.setText(currentMovie.getPseudo());

        TextView tweet = (TextView) listItem.findViewById(R.id.textView_tweet);
        tweet.setText(currentMovie.getTweet());

        TextView hashtag = (TextView) listItem.findViewById(R.id.textView_hashtag);
        hashtag.setText(currentMovie.getHashtag());

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getResources().getString(R.string.sharedPref),Context.MODE_PRIVATE);

        String userString = sharedPreferences.getString("user",null);

        Gson gson = new Gson();
        final User user = gson.fromJson(userString, User.class);

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(user.nickname.equals(currentMovie.getPseudo())){
                    Intent intent = new Intent(mContext, TweetModifyActivity.class);
                    intent.putExtra(mContext.getString(R.string.Int_nickname), currentMovie.getPseudo());
                    intent.putExtra(mContext.getString(R.string.Int_hashtags), currentMovie.getHashtag());
                    intent.putExtra(mContext.getString(R.string.Int_tweet), currentMovie.getTweet());
                    intent.putExtra(mContext.getString(R.string.Int_idTweet), currentMovie.getIdTweet());

                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }

            }
        });
        return listItem;
    }
}
