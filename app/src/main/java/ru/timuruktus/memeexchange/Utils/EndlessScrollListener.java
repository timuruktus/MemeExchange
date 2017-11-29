package ru.timuruktus.memeexchange.Utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener{


    private int invisibleThreshold = 5;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    private LinearLayoutManager linearLayoutManager;

    public EndlessScrollListener(LinearLayoutManager layoutManager){
        this.linearLayoutManager = layoutManager;
    }


    @Override
    public void onScrolled(RecyclerView view, int dx, int dy){
        int lastVisibleItemPosition = 0;
        int totalItemCount = 0;

        if(linearLayoutManager != null){
            totalItemCount = linearLayoutManager.getItemCount();
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        }

        if(totalItemCount < previousTotalItemCount){
            previousTotalItemCount = totalItemCount;
            if(totalItemCount == 0){
                loading = true;
            }
        }


        if(loading && (totalItemCount > previousTotalItemCount)){
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the invisibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if(!loading && (lastVisibleItemPosition + invisibleThreshold > totalItemCount)){
            onLoadMore(totalItemCount);
            loading = true;
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int offset);

}
