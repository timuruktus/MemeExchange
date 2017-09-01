package ru.timuruktus.memeexchange.RegisterPart;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.R;

public class RegisterFragment extends MvpAppCompatFragment implements IRegisterView{

    @InjectPresenter
    public RegisterPresenter registerPresenter;
    Unbinder unbinder;
    @BindView(R.id.loginEditText) EditText loginEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.emailEditText) EditText emailEditText;
    @BindView(R.id.registerButton) Button registerButton;
    @BindView(R.id.registerProgressBar) ProgressBar registerProgressBar;

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
    }

    @Override
    public void showLoginTakenError(){
        loginEditText.setError(loginTakenErrorText);
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
