package ru.timuruktus.memeexchange.RegisterPart;

import android.animation.Animator;


public interface IRegisterPresenter{

    void onCreateView();
    void onRegisterClick(String login, String password, String email);

}
