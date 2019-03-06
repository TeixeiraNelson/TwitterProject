package ch.ribeiropython.twitterproject;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class oneTweetAdapter extends ArrayAdapter<oneTweet> {

    private Context mContext;
    private List<oneTweet> moviesList = new ArrayList<>();

    public oneTweetAdapter(@NonNull Context context, @LayoutRes ArrayList<oneTweet> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }
/*
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Movie currentMovie = moviesList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(currentMovie.getmImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentMovie.getmName());

        TextView release = (TextView) listItem.findViewById(R.id.textView_release);
        release.setText(currentMovie.getmRelease());

        return listItem;
    }*/
}
