package ru.timuruktus.memeexchange.FeedPart;

import java.util.ArrayList;

import ru.timuruktus.memeexchange.POJO.Meme;

public interface IFeedPresenter {

    void loadFeed(boolean showLoading, int offset);
    void loadFeed(boolean showLoading, int offset, int pageSize);
    void loadMoreFeed(int offset, int pageSize);
    void loadFeedFromCache();
    void onCreateView(String tag);
    void onDestroyView();
    void onDestroyFragment();
    void refreshAllData(boolean showLoading);
    ArrayList<Meme> getNewestMemeData();

}
