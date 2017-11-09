package ru.timuruktus.memeexchange.MainPart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.timuruktus.memeexchange.Events.OpenFragment;
import ru.timuruktus.memeexchange.FeedPart.FeedFragment;
import ru.timuruktus.memeexchange.LoginPart.LoginFragment;
import ru.timuruktus.memeexchange.NewPostPart.NewPostFragment;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.RegisterPart.RegisterFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.FEED_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.LOGIN_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.NEWEST_FEED_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.NEW_POST_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.REGISTER_FRAGMENT_TAG;

public class MainActivity extends MvpAppCompatActivity implements IMainActivity{


    @InjectPresenter
    MainPresenter mainPresenter;
    public static final String DEFAULT_TAG = "DefaultTag";
    public static final String TESTING_TAG = "TestingTag";
    @BindView(R.id.newsImage) ImageView newsImage;
    @BindView(R.id.profileImage) ImageView profileImage;
    @BindView(R.id.exchangeImage) ImageView exchangeImage;
    @BindView(R.id.moreImage) ImageView moreImage;
    @BindView(R.id.menuTabs) ConstraintLayout menuTabs;

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),
            R.id.container){
        @Override
        protected Fragment createFragment(String fragmentTag, Object data){
            mainPresenter.setCurrentFragmentTag(fragmentTag);
            switch(fragmentTag){
                case FEED_FRAGMENT_TAG:
                case NEWEST_FEED_FRAGMENT_TAG:
                    return FeedFragment.getInstance(fragmentTag, (String) data);
                case LOGIN_FRAGMENT_TAG:
                    return LoginFragment.getInstance();
                case REGISTER_FRAGMENT_TAG:
                    return RegisterFragment.getInstance();
                case NEW_POST_FRAGMENT_TAG:
                    return NewPostFragment.getInstance();
                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void setupFragmentTransactionAnimation(Command command, Fragment currentFragment, Fragment nextFragment, FragmentTransaction fragmentTransaction){
            super.setupFragmentTransactionAnimation(command, currentFragment, nextFragment, fragmentTransaction);
//            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        @Override
        protected void showSystemMessage(String message){
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit(){
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        MyApp.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    protected void onPause(){
        super.onPause();
        MyApp.INSTANCE.getNavigatorHolder().removeNavigator();
    }

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy(){
        EventBus.getDefault().unregister(this);
        mainPresenter.onDestroy();
        super.onDestroy();

    }


    @Subscribe
    public void fragmentOpenListener(OpenFragment event){
        String tag = event.getFragmentTag();
        refreshAllMenuIcons();
        Log.d(TESTING_TAG, "fragmentOpenListener() tag = " + tag);
        switch(tag){
            case FEED_FRAGMENT_TAG:
                menuTabs.setVisibility(VISIBLE);
                newsImage.setImageResource(R.drawable.ic_menu_news_selected);
                break;
            case NEWEST_FEED_FRAGMENT_TAG:
                menuTabs.setVisibility(VISIBLE);
                profileImage.setImageResource(R.drawable.ic_menu_my_account_selected);
                break;
            case REGISTER_FRAGMENT_TAG:
                menuTabs.setVisibility(GONE);
                break;
            case LOGIN_FRAGMENT_TAG:
                menuTabs.setVisibility(GONE);
                break;
            case NEW_POST_FRAGMENT_TAG:
                menuTabs.setVisibility(VISIBLE);
                moreImage.setImageResource(R.drawable.ic_menu_more_selected);
                break;
            default:
                throw new RuntimeException("Unknown screen key!");
        }
    }

    private void refreshAllMenuIcons(){
        newsImage.setImageResource(R.drawable.ic_menu_news);
        profileImage.setImageResource(R.drawable.ic_menu_my_account);
        exchangeImage.setImageResource(R.drawable.ic_menu_graphic);
        moreImage.setImageResource(R.drawable.ic_menu_more);

    }

    @OnClick(R.id.newsImage)
    public void onNewsImageClicked(){
        if(mainPresenter.getCurrentFragmentTag().equals(FEED_FRAGMENT_TAG)){
            return;
        }
        MyApp.INSTANCE.getRouter().backTo(FEED_FRAGMENT_TAG);
    }

    @OnClick(R.id.profileImage)
    public void onProfileImageClicked(){
//        String userId = MyApp.getSettings().getUserObjectId();
//        MyApp.INSTANCE.getRouter().newRootScreen(NEWEST_FEED_FRAGMENT_TAG, userId);
    }

    @OnClick(R.id.exchangeImage)
    public void onExchangeImageClicked(){

    }

    @OnClick(R.id.moreImage)
    public void onMoreImageClicked(){
        PopupMenu menu = new PopupMenu(this, moreImage);
        menu.inflate(R.menu.bottom_nav_menu);
        menu.setOnMenuItemClickListener(getMorePopupListener());
        menu.show();
    }

    private PopupMenu.OnMenuItemClickListener getMorePopupListener(){
        return item -> {
            switch(item.getItemId()){
                case R.id.new_post:
                    MyApp.INSTANCE.getRouter().navigateTo(NEW_POST_FRAGMENT_TAG);
                    return true;
                case R.id.settings:
                    // TODO: Open SettingsFragment
                    return true;
                default:
                    return false;
            }
        };
    }

}
