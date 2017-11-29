package ru.timuruktus.memeexchange.FeedPart;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;

import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;

public interface IFeedView extends MvpView{


    @StateStrategyType(SingleStateStrategy.class)
    void showNewPosts(ArrayList<RecyclerItem> data);

    @StateStrategyType(SingleStateStrategy.class)
    void showNewUserPosts(ArrayList<RecyclerItem> data);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(boolean show);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showFirstOpenHint();

    //Maybe OneExecutionStateStrategy
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoadingIndicator(boolean show);

    @StateStrategyType(SkipStrategy.class)
    void showMessageNoInternetConnection();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMorePosts(ArrayList<Meme> memesList, int offset);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void clearRecyclerViewPool();
}
