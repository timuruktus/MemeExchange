package ru.timuruktus.memeexchange.FeedPart;

public interface IFeedPresenter {

    void loadFeed();
    void loadFeedFromCache();
    void onCreateView();
    void onDestroyView();
}
