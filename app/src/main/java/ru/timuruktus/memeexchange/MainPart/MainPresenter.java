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

import static ru.timuruktus.memeexchange.FeedPart.FeedPresenter.NEWEST_FEED_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

@InjectViewState
public class MainPresenter extends MvpPresenter<IMainActivity> implements IMainPresenter {

    public static final String FEED_FRAGMENT_TAG = "feedBackStackTag";
    public static final String LOGIN_FRAGMENT_TAG = "loginTag";
    public static final String REGISTER_FRAGMENT_TAG = "registerTag";
    private static String currentFragmentTag;

    private void loadFirstFragment(){
        ISettings settings = MyApp.INSTANCE.getSettings();
        if(settings.isUserLoggedIn()){
            MyApp.INSTANCE.getRouter().newRootScreen(FEED_FRAGMENT_TAG, NEWEST_FEED_TAG);
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
        Log.d(TESTING_TAG, "New tag is started setted");
        if(MainPresenter.currentFragmentTag == null || !MainPresenter.currentFragmentTag.equals(currentFragmentTag)){
            Log.d(TESTING_TAG, "New tag is setted");
            MainPresenter.currentFragmentTag = currentFragmentTag;

        }
        Log.d(TESTING_TAG, "New tag isnt started setted");
    }

    @Override
    public String getCurrentFragmentTag(){
        return MainPresenter.currentFragmentTag;
    }
}
