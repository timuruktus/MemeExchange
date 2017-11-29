package ru.timuruktus.memeexchange.RegisterPart;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;


import ru.timuruktus.memeexchange.Model.MemeDataManager;
import ru.timuruktus.memeexchange.Model.UserDataManager;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.Utils.FieldsValidator;
import rx.Observer;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

@InjectViewState
public class RegisterPresenter  extends MvpPresenter<IRegisterView> implements IRegisterPresenter{



    public static final String EMAIL_IS_UNAVAILABLE_MESSAGE = "HTTP 400 Bad Request";
    public static final String LOGIN_IS_UNAVAILABLE_MESSAGE = "HTTP 409 Conflict";


    @Override
    public void onCreateView(){

    }

    @Override
    public void onRegisterClick(String login, String password, String email){
        if(!isFieldsValid(login, password, email)){
            return;
        }
        getViewState().showLoadingIndicator(true);
        UserDataManager userDataManager = UserDataManager.getInstance();
        userDataManager.registerUser(login, password, email).subscribe(new Observer<User>(){
            @Override
            public void onCompleted(){
                Log.d(TESTING_TAG, "onRegisterClick.onCompleted() in RegisterPresenter");
                getViewState().showLoadingIndicator(false);
                getViewState().buildAnimations();
                getViewState().onRegisterSuccess();
            }

            @Override
            public void onError(Throwable e){
                Log.d(TESTING_TAG, "onRegisterClick.onError() in RegisterPresenter");
                getViewState().showLoadingIndicator(false);
                String message = e.getMessage();
                e.printStackTrace();
                // TODO: Custom backendless errors
                if(message.equals(EMAIL_IS_UNAVAILABLE_MESSAGE)){
                    getViewState().showEmailTakenError();
                }else if(message.equals(LOGIN_IS_UNAVAILABLE_MESSAGE)){
                    getViewState().showLoginTakenError();
                }else{
                    getViewState().showSomeError();
                }

            }

            @Override
            public void onNext(User user){
                Log.d(TESTING_TAG, "onRegisterClick.onNext() in RegisterPresenter");
                //TODO: save settings
            }
        });

    }

    private boolean isFieldsValid(String login, String password, String email){
        if(!FieldsValidator.isRegisterLoginValid(login)){
            getViewState().showLoginError();
            return false;
        }
        if(!FieldsValidator.isRegisterPasswordValid(password)){
            getViewState().showPasswordError();
            return false;
        }
        if(!FieldsValidator.isRegisterEmailValid(email)){
            getViewState().showEmailError();
            return false;
        }
        return true;
    }



}
