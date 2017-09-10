package ru.timuruktus.memeexchange.FeedPart;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ru.timuruktus.memeexchange.Events.OpenFragment;
import ru.timuruktus.memeexchange.MainPart.MainPresenter;
import ru.timuruktus.memeexchange.Model.DataManager;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.EndlessScrollListener;
import ru.timuruktus.memeexchange.Utils.SoundUtils;
import rx.Observer;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.FEED_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.Model.DataManager.DEFAULT_PAGE_SIZE;
import static ru.timuruktus.memeexchange.Utils.SoundUtils.MEDIUM_PRIORITY_SOUND;

@InjectViewState
public class FeedPresenter extends MvpPresenter<IFeedView> implements IFeedPresenter{


    private DataManager dataManager;
    private FeedAdapter infiniteListView;
    private static ArrayList<Meme> memeData = new ArrayList<>();
    private static String currentTag;
    public static final String BUNDLE_TAG = "bundleTag";
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
        // Clear previous data
        memeData.clear();
        getViewState().showError(false);
        getViewState().showLoadingIndicator(showLoading);
        dataManager.loadMemesFromWeb(pageSize, offset)
                .subscribe(new Observer<List<Meme>>(){
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
                    public void onNext(List<Meme> memesList){
                        memeData.addAll(memesList);
                        getViewState().showNewPosts(getNewestMemeData());
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
                    public void onCompleted(){}

                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        getViewState().showMessageNoInternetConnection();
                    }

                    @Override
                    public void onNext(List<Meme> memesList){
                        memeData.addAll(memesList);
                        getViewState().showMorePosts(memesList, offset);
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
                        } else{
                            getViewState().showNewPosts(memesList);
                        }
                    }
                });
    }

    @Override
    public void onCreateView(String newTag){
        if(dataManager == null){
            dataManager = DataManager.getInstance();
        }
        if(currentTag != null && !currentTag.equals(newTag)){
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
    public ArrayList<Meme> getNewestMemeData(){
        return memeData;
    }




}
