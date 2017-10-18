package ru.timuruktus.memeexchange.MainPart;

import android.app.Application;


import io.realm.Realm;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.ISettings;
import ru.timuruktus.memeexchange.Utils.Settings;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static ru.timuruktus.memeexchange.MainPart.MainPresenter.LOGIN_FRAGMENT_TAG;

public class MyApp extends Application {

    public static MyApp INSTANCE;
    private Cicerone<Router> cicerone;
    private static ISettings settings;


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        cicerone = Cicerone.create();
        settings = Settings.getInstance(getApplicationContext());
        Realm.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public void logout(){
        getSettings().setUserObjectId(null);
        getSettings().setUserToken(null);
        getRouter().newRootScreen(LOGIN_FRAGMENT_TAG);
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public static ISettings getSettings(){
        return settings;
    }

    public static void setSettings(ISettings settings){
        MyApp.settings = settings;
    }
}
