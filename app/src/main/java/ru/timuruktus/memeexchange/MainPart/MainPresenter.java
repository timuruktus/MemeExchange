package ru.timuruktus.memeexchange.MainPart;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import io.realm.Realm;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.Settings;

public class MainPresenter implements IMainPresenter {

    private IMainActivity mainActivity;
    private Context context;
    private Fragment fragment;

    public MainPresenter(IMainActivity mainActivity) {
        this.mainActivity = mainActivity;
        context = mainActivity.getApplicationContext();
        Settings.initSettings(context);
        Realm.init(context);
        loadFirstFragment();
    }


    /**
     * Call it after Activity re-creating
     * @param mainActivity
     */
    @Override
    public void refreshActivityLink(IMainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    private void loadFirstFragment(){
        if(Settings.isFirstOpen()){
            // TODO: open welcome screen
        }else{
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
    public void onDestroy() {

    }
}
