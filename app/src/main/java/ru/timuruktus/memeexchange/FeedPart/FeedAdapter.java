package ru.timuruktus.memeexchange.FeedPart;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softw4re.views.InfiniteListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.timuruktus.memeexchange.MainPart.GlideApp;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.R;

import static android.view.View.GONE;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

/**
 * We should retrieve User (author) and get author username and image
 * separately with meme retrieving
 */
public class FeedAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<Meme> itemList;

    public FeedAdapter(Activity activity, ArrayList<Meme> itemList){
        this.itemList = itemList;
        this.activity = activity;
    }


    @Override
    public int getCount(){
        return itemList.size();
    }

    @Override
    public Object getItem(int position){
        return itemList.get(position);
    }

    @Override
    @Deprecated
    public long getItemId(int position){
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup){
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meme_layout, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Meme meme = itemList.get(position);

        configureAuthorContainer(viewHolder, meme);
        setMemeText(viewHolder, meme.getText());
        setLongTextExpand(viewHolder);
        setLikeButtonImage(viewHolder, meme);
        loadImages(viewHolder, meme);

        return convertView;
    }

    private void configureAuthorContainer(ViewHolder viewHolder, Meme meme){
        viewHolder.authorName.setText(meme.getAuthor().getName());
        String likes = activity.getResources().getQuantityString(R.plurals.likes,
                (int) meme.getLikes(), meme.getLikes());
        viewHolder.likesCount.setText(likes);
    }

    private void setMemeText(ViewHolder viewHolder, String text){
        if(text != null){
            viewHolder.memeText.setVisibility(View.VISIBLE);
            viewHolder.memeText.setText(text);
        }else{
            viewHolder.memeText.setVisibility(GONE);
        }
    }

    private void loadImages(ViewHolder viewHolder, Meme meme){
        GlideApp.with(activity.getBaseContext())
                .load(meme.getImage())
                .centerCrop()
                .into(viewHolder.memeImage);

        GlideApp.with(activity.getBaseContext())
                .load(meme.getAuthor().getAvatar())
                .circleCrop()
                .into(viewHolder.authorImage);
    }

    private void setLikeButtonImage(ViewHolder viewHolder, Meme meme){
        if(meme.isUserLiked()){
            viewHolder.likeButton.setImageResource(R.drawable.ic_like);
        } else{
            viewHolder.likeButton.setImageResource(R.drawable.ic_not_yet_like);
        }
    }

    private void setLongTextExpand(ViewHolder viewHolder){

        if(viewHolder.memeText.getLineCount() >= 5){
            viewHolder.memeText.setMaxLines(5);
            viewHolder.textExpandButton.setVisibility(View.VISIBLE);
        }else{
            viewHolder.memeText.setMaxLines(Integer.MAX_VALUE);
            viewHolder.textExpandButton.setVisibility(GONE);
        }
    }

    static class ViewHolder{
        @BindView(R.id.authorImage) ImageView authorImage;
        @BindView(R.id.authorName) TextView authorName;
        @BindView(R.id.likes) TextView likesCount;
        @BindView(R.id.memeText) TextView memeText;
        @BindView(R.id.memeImage) ImageView memeImage;
        @BindView(R.id.moreButton) ImageView moreButton;
        @BindView(R.id.likeButton) ImageView likeButton;
        @BindView(R.id.textContainer) RelativeLayout textContainer;
        @BindView(R.id.textExpandButton) ImageView textExpandButton;

        @OnClick(R.id.textExpandButton)
        void onExpandClick(){
            memeText.setMaxLines(Integer.MAX_VALUE);
            textExpandButton.setVisibility(GONE);
        }

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
