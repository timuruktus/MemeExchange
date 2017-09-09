package ru.timuruktus.memeexchange.LoginPart;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ILoginView extends MvpView{

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startAnimations();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showLoginError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showPasswordError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showWrongDataError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMaybeEmailNotConfirmedError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showNoInternetConnectionError();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoadingIndicator(boolean show);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void clearLoginField();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void clearPasswordField();


}
