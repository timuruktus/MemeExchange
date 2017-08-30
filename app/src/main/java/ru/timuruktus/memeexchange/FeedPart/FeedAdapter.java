package ru.timuruktus.memeexchange.FeedPart;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appolica.flubber.Flubber;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.timuruktus.memeexchange.MainPart.GlideApp;
import ru.timuruktus.memeexchange.Model.DatabaseHelper;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.R;

import static android.view.View.GONE;

/**
 * We should retrieve User (author) and get author username and image
 * separately with meme retrieving
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    public FeedAdapter(Activity activity, ArrayList<Meme> itemList, AdapterEventListener adapterEventListener){
        this.activity = activity;
        this.itemList = itemList;
        this.adapterEventListener = adapterEventListener;
    }

    private Activity activity;
    private ArrayList<Meme> itemList;
    private AdapterEventListener adapterEventListener;


    public interface AdapterEventListener{
        void onLiked(Meme meme);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.meme_layout, parent, false);
        return new ViewHolder(view, adapterEventListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.setData(itemList.get(position));
    }

    @Override
    public long getItemId(int position){
        return itemList.get(position).getObjectId().hashCode();
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.authorImage) ImageView authorImage;
        @BindView(R.id.authorName) TextView authorName;
        @BindView(R.id.likes) TextView likesCount;
        @BindView(R.id.memeText) TextView memeText;
        @BindView(R.id.memeImage) ImageView memeImage;
        @BindView(R.id.moreButton) ImageView moreButton;
        @BindView(R.id.likeButton) ImageView likeButton;
        @BindView(R.id.textContainer) RelativeLayout textContainer;
        @BindView(R.id.textExpandButton) ImageView textExpandButton;
        private View view;
        private AdapterEventListener adapterEventListener;
        private Meme meme;

        @OnClick(R.id.textExpandButton)
        void onExpandClick(){
            memeText.setMaxLines(Integer.MAX_VALUE);
            textExpandButton.setVisibility(GONE);
        }

        @OnClick(R.id.likeButton)
        void onLikeClicked(){
            if(meme.isUserLiked()){
                meme.setLikes(meme.getLikes() - 1);
            } else{
                meme.setLikes(meme.getLikes() + 1);
            }
            meme.setUserLiked(!meme.isUserLiked());
            DatabaseHelper.getInstance().updateMeme(meme);
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN)
                    .duration(1000)
                    .createFor(likeButton)
                    .start();
            adapterEventListener.onLiked(meme);
            setLikeButtonImage(meme);
            configureAuthorContainer(meme);
        }

        void setData(Meme meme){
            this.meme = meme;
            configureAuthorContainer(meme);
            setMemeText(meme.getText());
            setLongTextExpand();
            setLikeButtonImage(meme);
            loadImages(meme);
        }

        private void configureAuthorContainer(Meme meme){
            authorName.setText(meme.getAuthor().getName());
            String likes = view.getResources().getQuantityString(R.plurals.likes,
                    (int) meme.getLikes(), meme.getLikes());
            likesCount.setText(likes);
        }

        private void setMemeText(String text){
            if(text != null){
                memeText.setVisibility(View.VISIBLE);
                memeText.setText(text);
            } else{
                memeText.setVisibility(GONE);
            }
        }

        private void loadImages(Meme meme){
            GlideApp.with(view.getContext())
                    .load(meme.getImage())
                    .centerCrop()
                    .into(memeImage);

            GlideApp.with(view.getContext())
                    .load(meme.getAuthor().getAvatar())
                    .circleCrop()
                    .into(authorImage);
        }

        private void setLikeButtonImage(Meme meme){
            if(meme.isUserLiked()){
                likeButton.setImageResource(R.drawable.ic_like);
            } else{
                likeButton.setImageResource(R.drawable.ic_not_yet_like);
            }
        }

        private void setLongTextExpand(){

            if(memeText.getLineCount() >= 5){
                memeText.setMaxLines(5);
                textExpandButton.setVisibility(View.VISIBLE);
            } else{
                memeText.setMaxLines(Integer.MAX_VALUE);
                textExpandButton.setVisibility(GONE);
            }
        }

        ViewHolder(View view, AdapterEventListener adapterEventListener){
            super(view);
            this.adapterEventListener = adapterEventListener;
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
