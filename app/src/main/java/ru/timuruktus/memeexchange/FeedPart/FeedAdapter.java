package ru.timuruktus.memeexchange.FeedPart;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softw4re.views.InfiniteListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.R;

/**
 * We should retrieve User (author) and get author username and image
 * separately with meme retrieving
 */
public class FeedAdapter extends InfiniteListAdapter<Meme> {


    private IFeedView fragment;
    private Activity activity;
    private int itemLayoutRes;
    private ArrayList<Meme> itemList;
    private TextView authorName, likesCount, memeText;
    private ImageView authorImage, memeImage, likeButton, moreButton;

    public FeedAdapter(Activity activity, int itemLayoutRes, ArrayList<Meme> itemList,
                       IFeedView fragment) {
        super(activity, itemLayoutRes, itemList);
        this.fragment = fragment;
        this.itemList = itemList;
        this.itemLayoutRes = itemLayoutRes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meme_layout, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Meme meme = itemList.get(position);

        viewHolder.authorName.setText(meme.getAuthor().getName());

        Glide.with(activity).load(meme.getUrl()).into(viewHolder.memeImage);

        if (meme.isUserLiked()) {
            viewHolder.likeButton.setImageResource(R.drawable.ic_like);
        } else {
            viewHolder.likeButton.setImageResource(R.drawable.ic_not_yet_like);
        }

        return convertView;
    }

    @Override
    public void onNewLoadRequired() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(int i) {

    }

    @Override
    public void onItemLongClick(int i) {

    }

    static class ViewHolder {
        @BindView(R.id.authorImage) ImageView authorImage;
        @BindView(R.id.authorName) TextView authorName;
        @BindView(R.id.likesCount) TextView likesCount;
        @BindView(R.id.memeText) TextView memeText;
        @BindView(R.id.memeImage) ImageView memeImage;
        @BindView(R.id.moreButton) ImageView moreButton;
        @BindView(R.id.likeButton) ImageView likeButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
