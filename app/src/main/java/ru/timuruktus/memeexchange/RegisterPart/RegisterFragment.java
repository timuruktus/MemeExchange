package ru.timuruktus.memeexchange.RegisterPart;


import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.appolica.flubber.Flubber;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.Events.OpenFragment;
import ru.timuruktus.memeexchange.MainPart.MyApp;
import ru.timuruktus.memeexchange.R;

import static ru.timuruktus.memeexchange.MainPart.MainPresenter.FEED_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.LOGIN_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.REGISTER_FRAGMENT_TAG;

public class RegisterFragment extends MvpAppCompatFragment implements IRegisterView{

    @InjectPresenter
    public RegisterPresenter registerPresenter;
    Unbinder unbinder;
    @BindView(R.id.loginEditText) EditText loginEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.emailEditText) EditText emailEditText;
    @BindView(R.id.registerButton) Button registerButton;
    @BindView(R.id.registerProgressBar) ProgressBar registerProgressBar;
    @BindView(R.id.doneView) LottieAnimationView doneView;
    @BindView(R.id.doneLayout) RelativeLayout doneLayout;
    @BindView(R.id.registerContainer) RelativeLayout registerContainer;

    @BindString(R.string.password_error) String passwordErrorText;
    @BindString(R.string.login_error) String loginErrorText;
    @BindString(R.string.email_error) String emailErrorText;
    @BindString(R.string.email_taken_error) String emailTakenErrorText;
    @BindString(R.string.login_taken_error) String loginTakenErrorText;


    private Context context;
    public final static String REGISTER_TAG = "registerTag";

    public static RegisterFragment getInstance(){
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(
                R.layout.register_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();
        registerPresenter.onCreateView();
        EventBus.getDefault().post(new OpenFragment(REGISTER_FRAGMENT_TAG));
        return view;
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.registerButton)
    void onRegisterClick(){
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        registerPresenter.onRegisterClick(login, password, email);
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
    public void showEmailError(){
        emailEditText.setError(emailErrorText);
    }

    @Override
    public void showEmailTakenError(){
        emailEditText.setError(emailTakenErrorText);
        Toast.makeText(context, R.string.email_taken_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginTakenError(){
        loginEditText.setError(loginTakenErrorText);
        Toast.makeText(context, R.string.login_taken_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSomeError(){
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showDoneView(){
        Animator animator = Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .duration(1000)
                .createFor(doneLayout);

        animator.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation){

            }

            @Override
            public void onAnimationEnd(Animator animation){
                doneView.setAlpha(1f);
                doneView.setSpeed(0.7f);
                doneView.playAnimation();
                doneView.addAnimatorListener(new Animator.AnimatorListener(){
                    @Override
                    public void onAnimationStart(Animator animation){
                    }

                    @Override
                    public void onAnimationEnd(Animator animation){
                        MyApp.INSTANCE.getRouter().backTo(null);
                        Toast.makeText(context, R.string.confirm_email, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation){

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation){

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation){

            }

            @Override
            public void onAnimationRepeat(Animator animation){

            }
        });
        animator.start();

    }

    @Override
    public void showLoadingIndicator(boolean show){
        if(show){
            registerButton.setVisibility(View.INVISIBLE);
            registerProgressBar.setVisibility(View.VISIBLE);
        }else{
            registerButton.setVisibility(View.VISIBLE);
            registerProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
