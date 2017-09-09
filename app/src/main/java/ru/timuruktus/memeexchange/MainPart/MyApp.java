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

public class MyApp extends Application {

    public static MyApp INSTANCE;
    private Cicerone<Router> cicerone;
    private ISettings settings;


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

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public ISettings getSettings(){
        return settings;
    }

    public void setSettings(ISettings settings){
        this.settings = settings;
    }
}
