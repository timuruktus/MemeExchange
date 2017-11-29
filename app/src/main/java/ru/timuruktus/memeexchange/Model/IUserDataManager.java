package ru.timuruktus.memeexchange.Model;

import ru.timuruktus.memeexchange.POJO.User;
import rx.Observable;

public interface IUserDataManager extends IDataManager{

    Observable<User> loginUser(String login, String password);
    Observable<User> registerUser(String login, String password, String email);
}
