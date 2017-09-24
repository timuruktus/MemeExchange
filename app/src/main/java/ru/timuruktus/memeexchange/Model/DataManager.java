package ru.timuruktus.memeexchange.Model;

import android.util.Log;
import android.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.timuruktus.memeexchange.Errors.NullTokenException;
import ru.timuruktus.memeexchange.MainPart.MyApp;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.RESTBodies.POSTLogin;
import ru.timuruktus.memeexchange.POJO.RESTBodies.POSTRegister;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.REST.BackendlessAPI;
import ru.timuruktus.memeexchange.Utils.NewestMemeComparator;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;
import static ru.timuruktus.memeexchange.REST.BackendlessAPI.BASE_URL;

public class DataManager implements IDataManager {


    private static final int LOAD_SHOPS_TIMEOUT = 10; // In seconds
    private static final int RETRY_COUNT = 3;
    private static Retrofit backendlessRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static DataManager dataManager;
    public final static int DEFAULT_PAGE_SIZE = 10;
    public static final String SORT_BY_CREATED_TIME = "created desc";


    public static DataManager getInstance(){
        if(dataManager == null){
            dataManager = new DataManager();
        }
        return dataManager;
    }

    @Override
    public String getAuthorNameByLogin(String login){
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
//        backendlessAPI.getUserByCondition(login).enqueue(new Callback<User>() {
//        // TODO
//        @Override
//        public void onResponse(Call<User> call, Response<User> response) {
//
//        }
//
//        @Override
//        public void onFailure(Call<User> call, Throwable t) {
//
//        }
//    });
        return null;
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


    @Override
    public Observable<List<Meme>> loadMemesFromWeb(int pageSize, int offset){
        return loadMemesFromWeb(pageSize, offset, SORT_BY_CREATED_TIME);
    }

    @Override
    public Observable<List<Meme>> loadMemesFromWeb(int pageSize, int offset, String sortBy) {
        Log.d(DEFAULT_TAG, "Try to load from web");
        return  downloadAndCacheMemesFromWeb(pageSize, offset, sortBy);
    }




    private Observable<List<Meme>> downloadAndCacheMemesFromWeb(int pageSize, int offset, String sortBy){
        final IDatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        return backendlessAPI
                .listNewestMemes(pageSize, offset, sortBy)
                .timeout(LOAD_SHOPS_TIMEOUT, TimeUnit.SECONDS)
                .retry(RETRY_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(memes -> {
                    databaseHelper.cacheMemes(memes);
                    return memes;
                });
    }

    @Override
    public Observable<List<Meme>> loadMemesFromCache() {
        Log.d(DEFAULT_TAG, "Try to load from cache");
        final IDatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        return databaseHelper.getMemes()
                .map(unsortedList -> {
            Collections.sort(unsortedList, new NewestMemeComparator());
            return unsortedList;
        });
    }

    @Override
    public Observable<List<Meme>> loadPopularGroups() {
        return null;
    }

    @Override
    public Observable<List<Meme>> loadPopularMemesByMonth(int pageSize, int offset) {
        return null;
    }

    @Override
    public Observable<List<Meme>> loadPopularMemesByWeek(int pageSize, int offset) {
        return null;
    }

    @Override
    public Observable<List<Meme>> loadPopularMemesToday(int pageSize, int offset) {
        return null;
    }


    @Override
    public Observable<Pair<List<User>, List<Meme>>> loadUserPosts(String userId, int pageSize, int offset){
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        String userToken = MyApp.getSettings().getUserToken();
        return Observable.zip(backendlessAPI.getUserByLogin(userId, userToken),
                backendlessAPI.getMemesFromGroup(userId, pageSize, offset, userToken),
                (author, posts) -> new Pair<>(author, posts))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
