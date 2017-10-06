package ru.timuruktus.memeexchange.MainPart;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.realm.Realm;
import ru.timuruktus.memeexchange.LoginPart.LoginFragment;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.ISettings;
import ru.timuruktus.memeexchange.Utils.Settings;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

@InjectViewState
public class MainPresenter extends MvpPresenter<IMainActivity> implements IMainPresenter {

    public static final String FEED_FRAGMENT_TAG = "feedBackStackTag";
    public static final String LOGIN_FRAGMENT_TAG = "loginTag";
    public static final String REGISTER_FRAGMENT_TAG = "registerTag";
    public static final String NEWEST_FEED_FRAGMENT_TAG = "newestFeedTag";
    public static final String NEW_POST_FRAGMENT_TAG = "newPostTag";
    private static String currentFragmentTag;

    private void loadFirstFragment(){
        ISettings settings = MyApp.getSettings();
        if(settings.isUserLoggedIn()){
            MyApp.INSTANCE.getRouter().newRootScreen(FEED_FRAGMENT_TAG);
        }else{
            MyApp.INSTANCE.getRouter().newRootScreen(LOGIN_FRAGMENT_TAG);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadFirstFragment();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setCurrentFragmentTag(String currentFragmentTag){
        if(MainPresenter.currentFragmentTag == null || !MainPresenter.currentFragmentTag.equals(currentFragmentTag)){
            MainPresenter.currentFragmentTag = currentFragmentTag;
        }
    }

    @Override
    public String getCurrentFragmentTag(){
        return MainPresenter.currentFragmentTag;
    }
}
