package ru.timuruktus.memeexchange.LoginPart;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.timuruktus.memeexchange.FeedPart.FeedFragment;
import ru.timuruktus.memeexchange.MainPart.MainPresenter;
import ru.timuruktus.memeexchange.Model.DataManager;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.FieldsValidator;
import ru.timuruktus.memeexchange.Utils.ISettings;
import ru.timuruktus.memeexchange.Utils.Settings;
import rx.Observer;

import static ru.timuruktus.memeexchange.FeedPart.FeedFragment.NEWEST_FEED_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.FEED_BACKSTACK_TAG;


@InjectViewState
public class LoginPresenter extends MvpPresenter<ILoginView> implements ILoginPresenter{

    public static final String WRONG_LOGIN_OR_PASSWORD_MESSAGE = "HTTP 401 Unauthorized";
    private Context context;

    public void onCreateView(Context context){
        this.context = context;
    }

    @Override
    protected void onFirstViewAttach(){
        super.onFirstViewAttach();
        getViewState().startAnimations();
    }

    // HTTP 401 Unauthorized- ERROR CODE
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
                FragmentManager fragmentManager = MainPresenter.fm;
                fragmentManager
                        .beginTransaction()
                        .add(R.id.container, FeedFragment.getInstance(NEWEST_FEED_TAG), FEED_BACKSTACK_TAG)
                        .commit();
            }

            @Override
            public void onError(Throwable e){
                getViewState().showLoadingIndicator(false);
                Log.d(DEFAULT_TAG, "Error = " + e.getMessage());
                if(e.getMessage().equals(WRONG_LOGIN_OR_PASSWORD_MESSAGE)){
                    Log.d(TESTING_TAG, "Inside");
                    getViewState().clearLoginField();
                    getViewState().clearPasswordField();
                    getViewState().showWrongDataError();
                }
            }

            @Override
            public void onNext(User user){
                String token = user.getToken();
                String objectId = user.getObjectId();
                ISettings settings = Settings.getInstance(context);
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
        //TODO: change to register fragment
        FragmentManager fragmentManager = MainPresenter.fm;
        fragmentManager
                .beginTransaction()
                .add(R.id.container, FeedFragment.getInstance(NEWEST_FEED_TAG), FEED_BACKSTACK_TAG)
                .commit();
    }
}
