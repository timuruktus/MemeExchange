package ru.timuruktus.memeexchange.Model;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static ru.timuruktus.memeexchange.REST.BackendlessAPI.BASE_URL;

public class DataManager implements IDataManager{

    static final int LOAD_SHOPS_TIMEOUT = 10; // In seconds
    static final int RETRY_COUNT = 3;
    static Retrofit backendlessRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static DataManager dataManager;
    public final static int PAGE_SIZE = 10;

    public static DataManager getInstance(){
        if(dataManager == null){
            dataManager = new DataManager();
        }
        return dataManager;
    }
}
