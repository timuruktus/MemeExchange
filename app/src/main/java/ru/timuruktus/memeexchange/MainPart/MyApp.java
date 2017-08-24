package ru.timuruktus.memeexchange.MainPart;

import android.app.Application;


import io.realm.Realm;
import ru.timuruktus.memeexchange.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
