package ru.timuruktus.memeexchange.LoginPart;

import android.content.Context;

public interface ILoginPresenter{

    void onJoinClick(String login, String password);
    void onRegisterClick();
}
