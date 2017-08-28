package ru.timuruktus.memeexchange.Utils;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class CustomFeedListenerListener implements SwipeRefreshLayout.OnRefreshListener{

    public CustomFeedListenerListener(ScrollEventListener scrollEventListener){
        this.scrollEventListener = scrollEventListener;
    }

    private ScrollEventListener scrollEventListener;

    @Override
    public void onRefresh(){
        scrollEventListener.onRefresh();
    }

    public interface ScrollEventListener{
        void onRefresh();
    }

}
