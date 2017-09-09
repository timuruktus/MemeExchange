package ru.timuruktus.memeexchange.FeedPart;

import android.support.v7.widget.LinearLayoutManager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;

public interface IFeedView extends MvpView {


    @StateStrategyType(SingleStateStrategy .class)
    void showNewPosts(List<Meme> memes);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(boolean show);
    //Maybe OneExecutionStateStrategy
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoadingIndicator(boolean show);
    @StateStrategyType(SkipStrategy.class)
    void showMessageNoInternetConnection();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMorePosts(List<Meme> memesList, int offset);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void clearRecyclerViewPool();
}
