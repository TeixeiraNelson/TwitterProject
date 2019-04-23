package ch.ribeiropython.twitterproject;

import android.content.Context;
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

    /*
        Adapter that handles the way the tweets are displayed.
     */

    private Context mContext;
    private List<oneTweet> tweetList = new ArrayList<>();

    public oneTweetAdapter(@NonNull Context context, ArrayList<oneTweet> list) {
        super(context, 0 , list);
        mContext = context;
        tweetList = list;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*
        Method that creates a tweet fragment for each tweet.
         */
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.tweet,parent,false);

        final oneTweet currentTweet = tweetList.get(position);

        TextView tweet = (TextView) listItem.findViewById(R.id.textView_tweet);
        tweet.setText(currentTweet.getTweet());

        TextView hashtag = (TextView) listItem.findViewById(R.id.textView_hashtag);
        hashtag.setText(currentTweet.getHashtag());

        //User user = User.getUserSession(oneTweetAdapter.this.getContext());

        TextView pseudo = (TextView) listItem.findViewById(R.id.textView_pseudo);
       // pseudo.setTextColor(mContext.getColor(R.color.redColor));
        pseudo.setText(currentTweet.getPseudo());


      /*  System.out.println("nick : " + user.nickname + " pseudo : " + currentTweet.getPseudo());

        if(user.nickname!=null)
            if(user.nickname.equals(currentTweet.getPseudo())) {

                //pseudo.setTextColor(this.mContext.getResources().getColor(R.color.redColor));
                TextView click = (TextView) listItem.findViewById(R.id.textView6);
                click.setVisibility(View.VISIBLE);

                /*
                Setting the on click listener only for the tweets that correspond to the user.
                 */
               /* listItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        User user = User.getUserSession(oneTweetAdapter.this.getContext());
                        Intent intent = new Intent(mContext, TweetModifyActivity.class);
                        intent.putExtra(mContext.getString(R.string.Int_nickname), currentTweet.getPseudo());
                        intent.putExtra(mContext.getString(R.string.Int_hashtags), currentTweet.getHashtag());
                        intent.putExtra(mContext.getString(R.string.Int_tweet), currentTweet.getTweet());
                        intent.putExtra(mContext.getString(R.string.Int_idTweet), currentTweet.getIdTweet());

                        mContext.startActivity(intent);
                    }
                });
            }*/

        return listItem;
    }
}
