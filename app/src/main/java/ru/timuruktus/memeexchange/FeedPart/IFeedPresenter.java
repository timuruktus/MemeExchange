package ru.timuruktus.memeexchange.FeedPart;

public interface IFeedPresenter {

    void loadFeed(int offset);
    void loadFeed(boolean showLoading, int offset);
    void loadFeed(boolean showLoading, int offset, int pageSize);
    void loadMoreFeed(int offset, int pageSize);
    void loadFeedFromCache();
    void onCreateView();
    void onDestroyView();
    void onDestroyFragment();
    void saveAdapter(FeedAdapter feedAdapter);
    FeedAdapter getSavedAdapter();
}
