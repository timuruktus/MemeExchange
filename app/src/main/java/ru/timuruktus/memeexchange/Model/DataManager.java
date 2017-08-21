package ru.timuruktus.memeexchange.Model;

import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.REST.BackendlessAPI;
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


    public static DataManager getInstance(){
        if (dataManager != null){
            return dataManager;
        }else{
            dataManager = new DataManager();
            return dataManager;
        }
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

    /*
    Only presenter can work with the error. Don't handle errors here.
     */
    @Override
    public Observable<List<Meme>> loadMemesFromWeb() {
        Log.d(DEFAULT_TAG, "Try to load from web");
        return  downloadAndCacheMemesFromWeb().replay(3600, TimeUnit.SECONDS);
    }



    private Observable<List<Meme>> downloadAndCacheMemesFromWeb(){
        final IDatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        return backendlessAPI
                .listMemes()
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
    public Observable<List<Meme>> loadMemesFromCache(Realm realm) {
        Log.d(DEFAULT_TAG, "Try to load from cache");
        final IDatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        return databaseHelper.getMemes(realm);
    }

    @Override
    public Observable<List<Meme>> loadPopularGroups() {
        return null;
    }

    @Override
    public Observable<List<Meme>> loadPopularMemesByMonth() {
        return null;
    }

    @Override
    public Observable<List<Meme>> loadPopularMemesByWeek() {
        return null;
    }

    @Override
    public Observable<List<Meme>> loadPopularMemesToday() {
        return null;
    }
}
