package ru.timuruktus.memeexchange.FeedPart;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import ru.timuruktus.memeexchange.Utils.FieldsValidator;

import static android.view.View.GONE;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

/**
 * We should retrieve User (author) and get author username and image
 * separately with meme retrieving
 */
class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{


    FeedAdapter(Activity activity, ArrayList<Meme> itemList, AdapterEventListener adapterEventListener){
        this.activity = activity;
        this.itemList = itemList;
        this.adapterEventListener = adapterEventListener;

    }

    private Activity activity;
    private ArrayList<Meme> itemList;
    private AdapterEventListener adapterEventListener;


    interface AdapterEventListener{
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
        @BindView(R.id.textExpandButton) TextView textExpandButton;
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
            }else{
                meme.setLikes(meme.getLikes() + 1);
            }
            meme.setUserLiked(!meme.isUserLiked());

            DatabaseHelper.getInstance().updateMeme(meme);
            Flubber.with()
                    .animation(Flubber.AnimationPreset.SWING)
                    .duration(500)
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
            if(FieldsValidator.isStringEmpty(meme.getAuthor().getName())){
                authorName.setText(meme.getAuthor().getLogin());
            }else{
                authorName.setText(meme.getAuthor().getName());
            }
            String likes = view.getResources().getQuantityString(R.plurals.likes,
                    (int) meme.getLikes(), meme.getLikes());
            likesCount.setText(likes);
        }

        private void setMemeText(String text){
            if(!FieldsValidator.isStringEmpty(text)){
                memeText.setVisibility(View.VISIBLE);
                memeText.setText(text);
            }else{
                memeText.setVisibility(GONE);
            }
        }

        private void loadImages(Meme meme){
            memeImage.setVisibility(View.VISIBLE);
            if(!FieldsValidator.isStringEmpty(meme.getImage())){
                GlideApp.with(view.getContext())
                        .load(meme.getImage())
                        .centerCrop()
                        .into(memeImage);
            }else{
                memeImage.setVisibility(GONE);
            }

            GlideApp.with(view.getContext())
                    .load(meme.getAuthor().getAvatar())
                    .circleCrop()
                    .placeholder(R.drawable.ic_author_placeholder)
                    .into(authorImage);


        }

        private void setLikeButtonImage(Meme meme){
            if(meme.isUserLiked()){
                likeButton.setImageResource(R.drawable.ic_like);
            }else{
                likeButton.setImageResource(R.drawable.ic_not_yet_like);
            }
        }

        private void setLongTextExpand(){
            Log.d(TESTING_TAG, "memeText line count in FeedAdapter = " + memeText.getLineCount());
            memeText.setMaxLines(Integer.MAX_VALUE);
            if(memeText.getLineCount() > 5){
                textExpandButton.setVisibility(View.VISIBLE);
                memeText.setMaxLines(5);
                Log.d(TESTING_TAG, "(memeText.getLineCount() > 5 in FeedAdapter");
            }else{
                Log.d(TESTING_TAG, "(memeText.getLineCount() < 5 in FeedAdapter");
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
