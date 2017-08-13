package ru.timuruktus.memeexchange.MainPart;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.timuruktus.memeexchange.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements IMainActivity {


    private IMainPresenter mainPresenter;
    public static final String DEFAULT_TAG = "DefaultTag";
    public static final String TESTING_TAG = "TestingTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getLastNonConfigurationInstance() != null ||
                !(getSupportFragmentManager() instanceof IMainPresenter)) {
            mainPresenter = (IMainPresenter) getLastNonConfigurationInstance();
        }else{
            // Most likely this is the first launch
            mainPresenter = new MainPresenter(this);
            mainPresenter.onCreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh presenter's link to this activity.
        // Because onResume() starts after onCreate(), mainPresenter != null
        // so we can refresh link and always hold link to the newest MainActivity in presenter
        mainPresenter.refreshActivityLink(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
    }

    /**
     * Do not change this method. Before activity re-creation, we should save our link to
     * main presenter. If so, we can take it again in onCreate(), and if it's the first
     * app run, in onCreate() we create new MainPresenter instance.
     * @return
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mainPresenter;
    }

}
