package ru.timuruktus.memeexchange.RegisterPart;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface IRegisterView extends MvpView{


    @StateStrategyType(OneExecutionStateStrategy.class)
    void showLoginError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showPasswordError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showEmailError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showEmailTakenError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showLoginTakenError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSomeError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showConfirmEmailMessage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoadingIndicator(boolean show);
}
