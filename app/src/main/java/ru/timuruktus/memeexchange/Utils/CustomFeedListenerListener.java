package ru.timuruktus.memeexchange.Utils;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class CustomFeedListenerListener extends EndlessScrollListener implements SwipeRefreshLayout.OnRefreshListener{

    public CustomFeedListenerListener(LinearLayoutManager layoutManager, ScrollEventListener scrollEventListener){
        super(layoutManager);
        this.scrollEventListener = scrollEventListener;
    }

    private ScrollEventListener scrollEventListener;

    @Override
    public void onRefresh(){
        scrollEventListener.onRefresh();
    }

    public interface ScrollEventListener{
        void onLoadMore(int page, int offset);
        void onRefresh();
    }


    // Defines the process for actually loading more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load.
    @Override
    public void onLoadMore(int page, int offset){
        scrollEventListener.onLoadMore(page, offset);
    }
}
