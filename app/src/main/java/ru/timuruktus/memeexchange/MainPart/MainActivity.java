package ru.timuruktus.memeexchange.MainPart;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import io.realm.Realm;
import ru.timuruktus.memeexchange.FeedPart.FeedFragment;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.Settings;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static ru.timuruktus.memeexchange.FeedPart.FeedFragment.FEED_FRAGMENT_TAG;

public class MainActivity extends MvpAppCompatActivity implements IMainActivity {


    @InjectPresenter
    MainPresenter mainPresenter;
    public static final String DEFAULT_TAG = "DefaultTag";
    public static final String TESTING_TAG = "TestingTag";
    public static Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(realm == null){
            Realm.init(getApplicationContext());
            realm = Realm.getDefaultInstance();
        }
        super.onCreate(savedInstanceState);
        mainPresenter.initPresenter(getSupportFragmentManager(), getApplicationContext());
        setContentView(R.layout.activity_main);
        Log.d(TESTING_TAG, "onCreate() in MainActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        Log.d(TESTING_TAG, "onDestroy() in MainActivity");
        if(realm != null){
            realm.close();
        }
        mainPresenter.onDestroy();
        super.onDestroy();

    }


}