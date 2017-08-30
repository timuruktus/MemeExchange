package ru.timuruktus.memeexchange.LoginPart;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Random;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.R;

public class LoginFragment extends MvpAppCompatFragment implements ILoginView{

    @BindView(R.id.sky) RelativeLayout sky;
    @BindView(R.id.loginProgressBar) ProgressBar loginProgressBar;
    @BindView(R.id.happyCloud) ImageView happyCloud;
    @BindView(R.id.memeExchange) TextView memeExchange;
    @Nullable @BindView(R.id.welcome) TextView welcome;
    @BindView(R.id.loginTopContainer) RelativeLayout loginTopContainer;
    @BindView(R.id.loginEditText) EditText loginEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.joinContainer) RelativeLayout joinContainer;
    @BindView(R.id.notYetRegistered) TextView notYetRegistered;
    @BindView(R.id.registerButton) Button registerButton;
    @BindView(R.id.registerContainer) RelativeLayout registerContainer;
    @Nullable @BindView(R.id.pleaseLoginFirst) TextView pleaseLoginFirst;

    @BindString(R.string.password_error) String passwordErrorText;
    @BindString(R.string.login_error) String loginErrorText;
    @BindString(R.string.easter_egg_1) String easterEgg1Text;
    @BindString(R.string.easter_egg_3) String easterEgg3Text;

    private Context context;
    private static final int EASTER_EGG_0 = 0;
    private static final int EASTER_EGG_1 = 1;
    private static final int EASTER_EGG_2 = 2;
    @InjectPresenter
    public LoginPresenter loginPresenter;
    Unbinder unbinder;

    public static LoginFragment getInstance(){
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(
                R.layout.login_screen, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();
        loginPresenter.onCreateView(getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rollEasterEgg();
    }

    private void rollEasterEgg(){
        Random random = new Random();
        int easterEgg = random.nextInt(500);
        if(easterEgg == EASTER_EGG_0 && pleaseLoginFirst != null){
            pleaseLoginFirst.setText(easterEgg1Text);
        }else if(easterEgg == EASTER_EGG_1){
            happyCloud.setImageResource(R.drawable.ic_stormy_cloud);
        }else if(easterEgg == EASTER_EGG_2){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                memeExchange.setText(easterEgg3Text);
            }
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.loginButton)
    void onJoinClick(){
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.onJoinClick(login, password);
    }

    @OnClick(R.id.registerButton)
    void onRegisterClick(){
        loginPresenter.onRegisterClick();
    }


    @Override
    public void startAnimations(){
        // for Cloud
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN_LEFT)
                .interpolator(Flubber.Curve.BZR_EASE_OUT_SINE)
                .force(0.5f)
                .duration(2000)
                .createFor(happyCloud)
                .start();

        // for app name
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN_RIGHT)
                .interpolator(Flubber.Curve.BZR_EASE_OUT_SINE)
                .force(0.5f)
                .duration(2000)
                .createFor(memeExchange)
                .start();

    }

    @Override
    public void showLoginError(){
        loginEditText.setError(loginErrorText);
    }

    @Override
    public void showPasswordError(){
        passwordEditText.setError(passwordErrorText);
    }

    @Override
    public void clearLoginField(){
        loginEditText.setText("");
    }

    @Override
    public void clearPasswordField(){
        passwordEditText.setText("");
    }

    @Override
    public void showWrongDataError(){
        Toast.makeText(context, R.string.wrong_login_or_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator(boolean show){
        if(show){
            loginProgressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
        }else{
            loginProgressBar.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }
}
