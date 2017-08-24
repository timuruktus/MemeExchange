package ru.timuruktus.memeexchange.Utils;

public class CustomEndlessScrollListener extends EndlessScrollListener{

    private ScrollEventListener scrollEventListener;

    public interface ScrollEventListener{
        boolean onLoadMore(int page, int offset);
    }

    public CustomEndlessScrollListener(ScrollEventListener scrollEventListener){
        this.scrollEventListener = scrollEventListener;
    }

    public CustomEndlessScrollListener(int visibleThreshold, ScrollEventListener scrollEventListener){
        super(visibleThreshold);
        this.scrollEventListener = scrollEventListener;
    }

    public CustomEndlessScrollListener(int visibleThreshold, int startPage, ScrollEventListener scrollEventListener){
        super(visibleThreshold, startPage);
        this.scrollEventListener = scrollEventListener;
    }

    // Defines the process for actually loading more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load.
    @Override
    public boolean onLoadMore(int page, int offset){
        return scrollEventListener.onLoadMore(page, offset);
    }
}
