package ru.timuruktus.memeexchange.FeedPart;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;

public interface IFeedView extends MvpView {


    @StateStrategyType(SingleStateStrategy .class)
    void showPosts(List<Meme> memes);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(boolean show);
    //Maybe OneExecutionStateStrategy
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoadingIndicator(boolean show);
    @StateStrategyType(SkipStrategy.class)
    void showMessageNoInternetConnection();
    void adapterRefreshCall();
}
