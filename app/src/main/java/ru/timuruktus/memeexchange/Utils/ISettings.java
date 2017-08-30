package ru.timuruktus.memeexchange.Utils;

public interface ISettings {

    boolean isFirstOpen();
    void setFirstOpen(boolean firstSign);
    boolean isUserLoggedIn();
    String getUserToken();
    void setUserToken(String token);
    String getUserObjectId();
    void setUserObjectId(String objectId);

}
