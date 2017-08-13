package ru.timuruktus.memeexchange.FeedPart;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;

public interface IFeedView {


    void showPosts(List<Meme> memes);
    void showError(boolean show);
    void showProgressIndicator(boolean show);
    void showMessageNoInternetConnection();
}
