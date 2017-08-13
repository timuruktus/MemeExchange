package ru.timuruktus.memeexchange.MainPart;

public interface IMainPresenter {

    void onCreate();
    void onDestroy();
    void refreshActivityLink(IMainActivity mainActivity);
}
