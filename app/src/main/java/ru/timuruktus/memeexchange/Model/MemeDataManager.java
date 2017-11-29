package ru.timuruktus.memeexchange.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.timuruktus.memeexchange.MainPart.MyApp;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.Queues.SavedMemeQueue;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;
import ru.timuruktus.memeexchange.REST.BackendlessAPI;
import ru.timuruktus.memeexchange.Utils.NewestMemeComparator;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;

public class MemeDataManager extends DataManager implements IMemeDataManager{


    private static MemeDataManager memeDataManager;
    public static final String SORT_BY_CREATED_TIME = "created desc";


    public static MemeDataManager getInstance(){
        if(memeDataManager == null){
            memeDataManager = new MemeDataManager();
        }
        return memeDataManager;
    }



    @Override
    public Observable<List<Meme>> loadMemesFromWeb(int pageSize, int offset){
        return loadMemesFromWeb(pageSize, offset, SORT_BY_CREATED_TIME);
    }

    @Override
    public Observable<List<Meme>> loadMemesFromWeb(int pageSize, int offset, String sortBy) {
        return  downloadAndCacheMemesFromWeb(pageSize, offset, sortBy);
    }




    private Observable<List<Meme>> downloadAndCacheMemesFromWeb(int pageSize, int offset, String sortBy){
        final IMemeDatabaseHelper databaseHelper = MemeDatabaseHelper.getInstance();
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
        final IMemeDatabaseHelper databaseHelper = MemeDatabaseHelper.getInstance();
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
    public Observable<List<RecyclerItem>> loadUserPosts(String userId, int pageSize, int offset){
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        String userToken = MyApp.getSettings().getUserToken();
        return Observable.zip(backendlessAPI.getUserByLogin(userId, userToken),
                backendlessAPI.getMemesFromGroup(userId, pageSize, offset, userToken),
                (author, posts) -> {
                    List<RecyclerItem> items = new ArrayList<>();
                    items.addAll(author);
                    items.addAll(posts);
                    return items;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Meme>> loadSavedNonCreatedMemes(){
        final IMemeDatabaseHelper databaseHelper = MemeDatabaseHelper.getInstance();
        return null;
    }

    @Override
    public void saveNonCreatedMeme(SavedMemeQueue meme){
        final IMemeDatabaseHelper databaseHelper = MemeDatabaseHelper.getInstance();

    }
}
