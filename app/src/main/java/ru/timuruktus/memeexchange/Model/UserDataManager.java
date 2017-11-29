package ru.timuruktus.memeexchange.Model;

import java.util.concurrent.TimeUnit;

import ru.timuruktus.memeexchange.POJO.RESTBodies.POSTLogin;
import ru.timuruktus.memeexchange.POJO.RESTBodies.POSTRegister;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.REST.BackendlessAPI;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserDataManager extends DataManager implements IUserDataManager{

    private static UserDataManager userDataManager;
    public static final String SORT_BY_CREATED_TIME = "created desc";


    public static UserDataManager getInstance(){
        if(userDataManager == null){
            userDataManager = new UserDataManager();
        }
        return userDataManager;
    }



    @Override
    public Observable<User> loginUser(String login, String password){
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        POSTLogin body = new POSTLogin(login, password);
        return backendlessAPI.loginUser(body)
                .timeout(LOAD_SHOPS_TIMEOUT, TimeUnit.SECONDS)
                .retry(RETRY_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<User> registerUser(String login, String password, String email){
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        POSTRegister body = new POSTRegister(login, password, email);
        return backendlessAPI.registerUser(body)
                .timeout(LOAD_SHOPS_TIMEOUT, TimeUnit.SECONDS)
                .retry(RETRY_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
