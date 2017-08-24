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
import android.widget.ImageView;
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
public class FeedAdapter extends InfiniteListAdapter<Meme>{

    private IFeedView fragment;
    private Activity activity;
    private int itemLayoutRes;
    private ArrayList<Meme> itemList;
    private TextView authorName, likesCount, memeText;
    private ImageView authorImage, memeImage, likeButton, moreButton;

    public FeedAdapter(Activity activity, int itemLayoutRes, ArrayList<Meme> itemList,
                       IFeedView fragment){
        super(activity, itemLayoutRes, itemList);
        this.fragment = fragment;
        this.itemList = itemList;
        this.itemLayoutRes = itemLayoutRes;
        this.activity = activity;
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

        viewHolder.authorName.setText(meme.getAuthor().getName());
        String likes = activity.getResources().getQuantityString(R.plurals.likes,
                (int) meme.getLikes(), meme.getLikes());
        viewHolder.likesCount.setText(likes);


        Log.d(TESTING_TAG, "getView() meme test: " + meme.getText());
        if(meme.getText() != null){
            viewHolder.memeText.setVisibility(View.VISIBLE);
            Log.d(TESTING_TAG, "getView() meme text != null");
            viewHolder.memeText.setText(meme.getText());
            viewHolder.memeText.setMovementMethod(new ScrollingMovementMethod());
        }else{
            Log.d(TESTING_TAG, "getView() meme text == null");
            viewHolder.memeText.setVisibility(GONE);
        }

        if(viewHolder.memeText.getLineCount() >= 5){
            viewHolder.memeText.setMaxLines(5);
            viewHolder.textExpandButton.setVisibility(View.VISIBLE);
            // TODO: Сделать видимой стрелку вниз
        }else{
            viewHolder.memeText.setMaxLines(Integer.MAX_VALUE);
            viewHolder.textExpandButton.setVisibility(GONE);
            // TODO: Сделать стрелку вниз невидимой
        }


        GlideApp.with(getContext())
                .load(meme.getImage())
                .centerCrop()
                .into(viewHolder.memeImage);



        GlideApp.with(getContext())
                .load(meme.getAuthor().getAvatar())
                .circleCrop()
                .into(viewHolder.authorImage);

        if(meme.isUserLiked()){
            viewHolder.likeButton.setImageResource(R.drawable.ic_like);
        } else{
            viewHolder.likeButton.setImageResource(R.drawable.ic_not_yet_like);
        }

        return convertView;
    }

    @Override
    public void onNewLoadRequired(){
//        this.addAll();
    }

    @Override
    public void onRefresh(){
        fragment.adapterRefreshCall();
    }

    @Override
    public void onItemClick(int i){

    }

    @Override
    public void onItemLongClick(int i){

    }

//    // OBJECT == NULL
//    public void addNewItem(ListView listView, Meme newItem, Object object) {
//        this.itemList.add(newItem);
//        this.notifyDataSetChanged();
//    }
//
//    // OBJECT == NULL
//    public void clearList(ListView listView, Object object) {
//        this.itemList.clear();
//        this.notifyDataSetChanged();
//    }

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