package ru.timuruktus.memeexchange.FeedPart;

public interface IFeedPresenter {

    void loadFeed();
    void loadFeed(boolean showLoading);
    void loadFeedFromCache();
    void onCreateView();
    void onDestroyView();
    void onDestroyFragment();
    void saveAdapter(FeedAdapter feedAdapter);
    FeedAdapter getSavedAdapter();
}
