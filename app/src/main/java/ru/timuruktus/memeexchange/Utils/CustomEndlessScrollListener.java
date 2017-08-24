package ru.timuruktus.memeexchange.Utils;

public class CustomEndlessScrollListener extends EndlessScrollListener{


    @Override
    public boolean onLoadMore(int page, int totalItemsCount){
        return false;
    }
}
