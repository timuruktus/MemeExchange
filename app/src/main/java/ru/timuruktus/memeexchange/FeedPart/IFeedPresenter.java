package ru.timuruktus.memeexchange.FeedPart;

import android.content.Context;

import java.util.ArrayList;

import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;
import ru.timuruktus.memeexchange.POJO.User;

public interface IFeedPresenter {

    void loadFeed(boolean showLoading, int offset);
    void loadFeed(boolean showLoading, int offset, int pageSize);
    void loadFeed(boolean showLoading, int offset, int pageSize, String userId);
    void loadMoreFeed(int offset, int pageSize);
    void loadFeedFromCache();

    void onCreateView(String tag, String user);
    void onDestroyFragment();
    void refreshAllData(boolean showLoading);
    ArrayList<RecyclerItem> getNewestPosts();

    void onResume();
    void onMemeLiked(Meme meme);
    void onSubscribe(User user);

}
