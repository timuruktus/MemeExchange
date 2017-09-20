package ru.timuruktus.memeexchange.AccountPart;

public interface IAccountPresenter{

    void loadAuthor(String authorId);
    void loadFeed(String tag);
    void loadOwnersGroup(String authorId);
    void onCreateView(String newTag, String userId);
}
