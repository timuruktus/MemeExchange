package ru.timuruktus.memeexchange.RegisterPart;

public interface IRegisterPresenter{

    void onCreateView();
    void onRegisterClick(String login, String password, String email);
}
