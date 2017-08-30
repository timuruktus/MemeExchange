package ru.timuruktus.memeexchange.MainPart;


import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.MvpPresenter;

import io.realm.Realm;
import ru.timuruktus.memeexchange.FeedPart.FeedFragment;
import ru.timuruktus.memeexchange.LoginPart.LoginFragment;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.ISettings;
import ru.timuruktus.memeexchange.Utils.Settings;


public class MainPresenter extends MvpPresenter<IMainActivity> implements IMainPresenter {

    public static FragmentManager fm;
    static Context context;
    public static final String FEED_BACKSTACK_TAG = "feedBackStackTag";
    public static final String LOGIN_BACKSTACK_TAG = "loginTag";

    public MainPresenter() {
    }

    void initPresenter(FragmentManager fm, Context context){
        MainPresenter.fm = fm;
        MainPresenter.context = context;
    }

    private void loadFirstFragment(){
        ISettings settings = Settings.getInstance(context);
        if(settings.isUserLoggedIn()){
            fm.beginTransaction()
                    .add(R.id.container, FeedFragment.getInstance(FeedFragment.NEWEST_FEED_TAG), FEED_BACKSTACK_TAG)
                    .commit();
        }else{
            fm.beginTransaction()
                    .add(R.id.container, LoginFragment.getInstance(), LOGIN_BACKSTACK_TAG)
                    .commit();
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Settings.getInstance(context);
        Realm.init(context);
        loadFirstFragment();
    }

    @Override
    public void onDestroy() {
        Settings.closeSettings();
    }
}
