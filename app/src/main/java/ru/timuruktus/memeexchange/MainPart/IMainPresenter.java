package ru.timuruktus.memeexchange.MainPart;

import android.support.v4.app.FragmentManager;

public interface IMainPresenter {

    void onCreate();
    void onDestroy();
    void setCurrentFragmentTag(String currentFragmentTag);
    String getCurrentFragmentTag();
//    void loadFirstFragment();
}
