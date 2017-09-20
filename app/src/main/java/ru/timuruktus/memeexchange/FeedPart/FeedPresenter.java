package ru.timuruktus.memeexchange.FeedPart;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.timuruktus.memeexchange.Model.DataManager;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;
import ru.timuruktus.memeexchange.POJO.User;
import rx.Observable;
import rx.Observer;

import static ru.timuruktus.memeexchange.Model.DataManager.DEFAULT_PAGE_SIZE;

@InjectViewState
public class FeedPresenter extends MvpPresenter<IFeedView> implements IFeedPresenter{


    private DataManager dataManager;
    private static ArrayList<RecyclerItem> posts = new ArrayList<>();
    private static String currentTag;
    private static String currentUser;
    public static final String BUNDLE_TAG = "bundleTag";
    public static final String BUNDLE_AUTHOR = "author";
    public static final String NEWEST_FEED_TAG = "newestFeedTag";

    public FeedPresenter(){
    }

    @Override
    protected void onFirstViewAttach(){
        super.onFirstViewAttach();
        loadFeed(true, 0);
    }

    @Override
    public void loadFeed(boolean showLoading, int offset){
        loadFeed(showLoading, offset, DEFAULT_PAGE_SIZE);
    }

    @Override
    public void loadFeed(boolean showLoading, int offset, int pageSize){
        loadFeed(showLoading, offset, pageSize, null);
    }

    @Override
    public void loadFeed(boolean showLoading, int offset, int pageSize, String userId){
        posts.clear();
        getViewState().showError(false);
        getViewState().showLoadingIndicator(showLoading);
        Observable observable;
        if(userId == null){
            observable = dataManager.loadMemesFromWeb(pageSize, offset);
        }else{
            observable = dataManager.loadUserPosts(pageSize, offset, userId);
        }
        observable.subscribe(new Observer<Object>(){
                    @Override
                    public void onCompleted(){
                        getViewState().showLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        getViewState().showMessageNoInternetConnection();
                        loadFeedFromCache();
                    }

                    @Override
                    public void onNext(Object data){
                        if(data instanceof List){
                            posts.addAll((ArrayList<RecyclerItem>) data);
                            getViewState().showNewPosts(getNewestPosts());
                        }else{
                            Pair<List<User>, List<Meme>> pair = (Pair<List<User>, List<Meme>>) data;
                            // User
                            posts.addAll(pair.first);
                            // Memes
                            posts.addAll(pair.second);
                            getViewState().showNewPosts(getNewestPosts());
                        }

                    }
                });
    }

    @Override
    public void loadMoreFeed(int offset, int pageSize){
        getViewState().showError(false);
        getViewState().showLoadingIndicator(false);
        dataManager.loadMemesFromWeb(pageSize, offset)
                .subscribe(new Observer<List<Meme>>(){
                    @Override
                    public void onCompleted(){
                    }

                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        getViewState().showMessageNoInternetConnection();
                    }

                    @Override
                    public void onNext(List<Meme> memesList){
                        posts.addAll(memesList);
                        getViewState().showMorePosts((ArrayList<Meme>) memesList, offset);
                    }
                });
    }


    @Override
    public void loadFeedFromCache(){
        dataManager.loadMemesFromCache()
                .subscribe(new Observer<List<Meme>>(){
                    @Override
                    public void onCompleted(){
                        getViewState().showLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        getViewState().showLoadingIndicator(false);
                        getViewState().showError(true);
                    }

                    @Override
                    public void onNext(List<Meme> memesList){
                        if(memesList.size() == 0){
                            onError(new Throwable("Empty cache"));
                        }else{
                            posts.clear();
                            posts.addAll(memesList);
                            getViewState().showNewPosts(getNewestPosts());
                        }
                    }
                });
    }

    @Override
    public void onCreateView(String newTag, String newUser){
        if(dataManager == null){
            dataManager = DataManager.getInstance();
        }

        if(currentTag != null && !currentTag.equals(newTag)){
            loadFeed(true, 0);
        }
        if(currentUser != null && !currentUser.equals(newUser)){
            loadFeed(true, 0);
        }
        currentTag = newTag;
    }

    @Override
    public void onDestroyView(){

    }

    @Override
    public void onDestroyFragment(){
        if(dataManager != null){
            dataManager = null;
        }
    }

    @Override
    public void refreshAllData(boolean showLoading){
        getViewState().clearRecyclerViewPool();
        loadFeed(showLoading, 0);
    }

    @Override
    public ArrayList<RecyclerItem> getNewestPosts(){
        return posts;
    }


}
