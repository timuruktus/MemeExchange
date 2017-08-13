package ru.timuruktus.memeexchange.FeedPart;

public class FeedPresenter implements IFeedPresenter {


    private IFeedView feedView;

    public FeedPresenter(IFeedView feedView) {
        this.feedView = feedView;
    }

    @Override
    public void loadFeed() {

    }
}
