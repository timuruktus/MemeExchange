package ru.timuruktus.memeexchange.MainPart;


import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.MvpPresenter;

import io.realm.Realm;
import ru.timuruktus.memeexchange.FeedPart.FeedFragment;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.Settings;

import static ru.timuruktus.memeexchange.FeedPart.FeedFragment.NEWEST_FEED_TAG;

public class MainPresenter extends MvpPresenter<IMainActivity> implements IMainPresenter {

    static FragmentManager fm;
    static Context context;

    public MainPresenter() {
    }

    void initPresenter(FragmentManager fm, Context context){
        MainPresenter.fm = fm;
        MainPresenter.context = context;
    }

    private void loadFirstFragment(){
        if(Settings.isFirstOpen()){
            fm.beginTransaction().add(R.id.container, new FeedFragment(), NEWEST_FEED_TAG).commit();
            // TODO: open welcome screen
        }else{
            fm.beginTransaction().add(R.id.container, new FeedFragment(), NEWEST_FEED_TAG).commit();
            // TODO: open feed
        }
    }

    @Override
    public void onCreate() {

//        // Восстанавливаем уже созданный фрагмент
//        FragmentManager fm = mainActivity.getSupportFragmentManager();
//        fragment = (MyFragment) fm.findFragmentByTag(FRAGMENT_INSTANCE_NAME);
//        // Если фрагмент не сохранен, создаем новый экземпляр
//        if(fragment == null){
//            fragment = new MyFragment();
//            fm.beginTransaction().add(R.id.container, fragment, FRAGMENT_INSTANCE_NAME).commit();
//        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Settings.initSettings(context);
        Realm.init(context);
        loadFirstFragment();
    }

    @Override
    public void onDestroy() {

    }
}
