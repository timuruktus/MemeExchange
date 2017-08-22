package ru.timuruktus.memeexchange.FeedPart;

import com.softw4re.views.InfiniteListAdapter;
import com.softw4re.views.InfiniteListView;

import ru.timuruktus.memeexchange.POJO.Meme;

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
