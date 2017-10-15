package ru.timuruktus.memeexchange.LoginPart;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.timuruktus.memeexchange.FeedPart.FeedFragment;
import ru.timuruktus.memeexchange.MainPart.MainPresenter;
import ru.timuruktus.memeexchange.MainPart.MyApp;
import ru.timuruktus.memeexchange.Model.DataManager;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.RegisterPart.RegisterFragment;
import ru.timuruktus.memeexchange.Utils.FieldsValidator;
import ru.timuruktus.memeexchange.Utils.ISettings;
import ru.timuruktus.memeexchange.Utils.Settings;
import rx.Observer;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.FEED_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.REGISTER_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.RegisterPart.RegisterFragment.REGISTER_TAG;


@InjectViewState
public class LoginPresenter extends MvpPresenter<ILoginView> implements ILoginPresenter{

    public static final String WRONG_LOGIN_OR_PASSWORD_MESSAGE = "HTTP 401 Unauthorized";
    public static final String MAYBE_EMAIL_NOT_CONFIRMED = "HTTP 400 Bad Request";
    public static final String INTERNET_CONNECTION_IS_ABSENT = "Unable to resolve host \"api.backendless.com\": No address associated with hostname";


    @Override
    protected void onFirstViewAttach(){
        super.onFirstViewAttach();
        getViewState().startAnimations();
    }

    @Override
    public void onJoinClick(String login, String password){
        if(!isFieldsValid(login, password)){
            return;
        }
        getViewState().showLoadingIndicator(true);
        DataManager dataManager = DataManager.getInstance();
        dataManager.loginUser(login, password).subscribe(new Observer<User>(){
            @Override
            public void onCompleted(){
                getViewState().showLoadingIndicator(false);
                MyApp.INSTANCE.getRouter().newRootScreen(FEED_FRAGMENT_TAG);
            }

            @Override
            public void onError(Throwable e){
                e.printStackTrace();
                getViewState().showLoadingIndicator(false);
                if(e.getMessage().equals(WRONG_LOGIN_OR_PASSWORD_MESSAGE)){
                    getViewState().clearPasswordField();
                    getViewState().showWrongDataError();
                }else if(e.getMessage().equals(MAYBE_EMAIL_NOT_CONFIRMED)){
                    getViewState().showMaybeEmailNotConfirmedError();
                }else if(e.getMessage().equals(INTERNET_CONNECTION_IS_ABSENT)){
                    getViewState().showNoInternetConnectionError();
                }
            }

            @Override
            public void onNext(User user){
                String token = user.getToken();
                String objectId = user.getObjectId();
                ISettings settings = MyApp.getSettings();
                settings.setUserObjectId(objectId);
                settings.setUserToken(token);
            }
        });
    }

    private boolean isFieldsValid(String login, String password){
        if(FieldsValidator.isStringEmpty(login)){
            getViewState().showLoginError();
            return false;
        }
        if(!FieldsValidator.isJoinPasswordValid(password)){
            getViewState().showPasswordError();
            return false;
        }
        return true;
    }

    @Override
    public void onRegisterClick(){
        MyApp.INSTANCE.getRouter().navigateTo(REGISTER_FRAGMENT_TAG);
    }
}
