package ru.timuruktus.memeexchange.FeedPart;

import android.app.Activity;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.appolica.flubber.Flubber;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import ru.timuruktus.memeexchange.MainPart.GlideApp;
import ru.timuruktus.memeexchange.MainPart.MyApp;
import ru.timuruktus.memeexchange.Model.DatabaseHelper;
import ru.timuruktus.memeexchange.POJO.Footer;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.FieldsValidator;

import static android.view.DragEvent.ACTION_DRAG_LOCATION;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;


class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ArrayList<RecyclerItem> itemList;
    private AdapterEventListener adapterEventListener;
    private User user;
    private final static byte POST = 0;
    private final static byte HEADER = 1;
    private final static byte FOOTER = 2;

    FeedAdapter(Activity activity, ArrayList<RecyclerItem> itemList, AdapterEventListener adapterEventListener){
        this.activity = activity;
        this.itemList = itemList;
        this.adapterEventListener = adapterEventListener;
        if(itemList.get(0) instanceof User){
            user = (User) itemList.get(0);
        }
    }

    interface AdapterEventListener{
        void onLiked(Meme meme);

        void onSubscribe(User user);

        void onAuthorClicked(User user);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = activity.getLayoutInflater();
        if(viewType == HEADER){
            View view = inflater.inflate(R.layout.account_header, parent, false);
            return new HeaderViewHolder(view, adapterEventListener);
        }else if(viewType == FOOTER){
            // TODO if we have footer
            View view = inflater.inflate(R.layout.meme_layout, parent, false);
            return new FeedAdapter.ViewHolder(view, adapterEventListener);
        }else{
            // Usual Post
            View view = inflater.inflate(R.layout.meme_layout, parent, false);
            return new FeedAdapter.ViewHolder(view, adapterEventListener);
        }
    }

    @Override
    public int getItemViewType(int position){
        if(position == 0 && user != null){
            return HEADER;
        }else if(position == itemList.size() -  1 && itemList.get(itemList.size() - 1) instanceof Footer){
            return FOOTER;
        }else{
            return POST;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.setData(user);
        }else if(holder instanceof FeedAdapter.ViewHolder){
            FeedAdapter.ViewHolder postHolder = (FeedAdapter.ViewHolder) holder;
            Meme meme = (Meme) itemList.get(position);
            if(meme.getAuthor() != null){
                postHolder.setData((Meme) itemList.get(position));
            }
        }
    }

    @Override
    public long getItemId(int position){
        return itemList.get(position).hashCode();
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.authorImg) ImageView authorImage;
        @BindView(R.id.authorName) TextView authorName;
        @BindView(R.id.likes) TextView likesCount;
        @BindView(R.id.memeText) ExpandableTextView memeText;
        @BindView(R.id.memeImage) ImageView memeImage;
        @BindView(R.id.moreButton) ImageView moreButton;
        @BindView(R.id.likeButton) ImageView likeButton;
        @BindView(R.id.textContainer) RelativeLayout textContainer;
        @BindView(R.id.actionsContainer) RelativeLayout actionsContainer;

        private View view;
        private AdapterEventListener adapterEventListener;
        private Meme meme;


        ViewHolder(View view, AdapterEventListener adapterEventListener){
            super(view);
            this.adapterEventListener = adapterEventListener;
            this.view = view;
            ButterKnife.bind(this, view);

        }

        public void setData(Meme meme){
            this.meme = meme;
            configureAuthorContainer(meme);
            setMemeText(meme.getText());
            setLikeButtonImage(meme);
            loadImages(meme);
        }

        private void configureAuthorContainer(Meme meme){
            User author = meme.getAuthor();
            if(FieldsValidator.isStringEmpty(meme.getAuthor().getName())){
                authorName.setText(author.getLogin());
            }else{
                authorName.setText(author.getName());
            }
            authorName.setOnClickListener(v -> adapterEventListener.onAuthorClicked(author));
            authorImage.setOnClickListener(v -> adapterEventListener.onAuthorClicked(author));
            String likes = view.getResources().getQuantityString(R.plurals.likes,
                    (int) meme.getLikes(), meme.getLikes());
            likesCount.setText(likes);
        }

        private void setMemeText(String text){
            if(!FieldsValidator.isStringEmpty(text)){
                memeText.setVisibility(VISIBLE);
                memeText.setText(text);
            }else{
                memeText.setVisibility(GONE);
            }
        }

        private void loadImages(Meme meme){

            if(!FieldsValidator.isStringEmpty(meme.getImage())){
                memeImage.setVisibility(VISIBLE);
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

    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder{

        private AdapterEventListener listener;
        private User user;
        private View view;
        @BindView(R.id.authorImg) ImageView authorImg;
        @BindView(R.id.subBtn) Button subBtn;
        @BindView(R.id.likes) TextView likes;
        @BindView(R.id.subscribers) TextView subscribers;


        HeaderViewHolder(View itemView, AdapterEventListener listener){
            super(itemView);
            view = itemView;
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        public void setData(User user){
            this.user = user;
            loadImages();
            loadLikesText();
            loadSubText();
            configureSubBut();
        }

        private void configureSubBut(){
            String userId = user.getObjectId();
            if(userId.equals(MyApp.getSettings().getUserObjectId())){
                subBtn.setVisibility(GONE);
            }else{
                subBtn.setVisibility(VISIBLE);
            }
        }

        private void loadLikesText(){
            String likesText;
            long likesCount = user.getLikes();
            if(likesCount / 1000000 >= 1){
                likesText = likesCount + "M";
            }else if(likesCount / 1000 >= 1){
                likesText = likesCount + "K";
            }else{
                likesText = String.valueOf(likesCount);
            }
            likes.setText(likesText);
        }

        private void loadSubText(){
            String subscribesText;
            long subscribers = user.getSubscribers();
            if(subscribers / 1000000 >= 1){
                subscribesText = subscribers + "M";
            }else if(subscribers / 1000 >= 1){
                subscribesText = subscribers + "K";
            }else{
                subscribesText = String.valueOf(subscribers);
            }
            likes.setText(subscribesText);
        }

        private void loadImages(){
            GlideApp.with(view.getContext())
                    .load(user.getAvatar())
                    .circleCrop()
                    .placeholder(R.drawable.ic_author_placeholder)
                    .into(authorImg);
        }

        @OnClick(R.id.subBtn)
        public void onSubscribeClick(){
            listener.onSubscribe(user);
        }


    }
}
