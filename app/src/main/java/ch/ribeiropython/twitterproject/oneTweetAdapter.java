package ch.ribeiropython.twitterproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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



        TextView tweet = (TextView) listItem.findViewById(R.id.textView_tweet);
        tweet.setText(currentMovie.getTweet());

        TextView hashtag = (TextView) listItem.findViewById(R.id.textView_hashtag);
        hashtag.setText(currentMovie.getHashtag());

        User user = User.getUserSession(oneTweetAdapter.this.getContext());

        TextView pseudo = (TextView) listItem.findViewById(R.id.textView_pseudo);
        pseudo.setTextColor(mContext.getColor(R.color.redColor));
        pseudo.setText(currentMovie.getPseudo());

        if(user.nickname!=null)
            if(user.nickname.equals(currentMovie.getPseudo())) {

                pseudo.setTextColor(this.mContext.getResources().getColor(R.color.redColor));
                TextView click = (TextView) listItem.findViewById(R.id.textView6);
                click.setVisibility(View.VISIBLE);


                listItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        User user = User.getUserSession(oneTweetAdapter.this.getContext());
                        Intent intent = new Intent(mContext, TweetModifyActivity.class);
                        intent.putExtra(mContext.getString(R.string.Int_nickname), currentMovie.getPseudo());
                        intent.putExtra(mContext.getString(R.string.Int_hashtags), currentMovie.getHashtag());
                        intent.putExtra(mContext.getString(R.string.Int_tweet), currentMovie.getTweet());
                        intent.putExtra(mContext.getString(R.string.Int_idTweet), currentMovie.getIdTweet());

                        mContext.startActivity(intent);
                    }
                });
            }
        return listItem;
    }
}
