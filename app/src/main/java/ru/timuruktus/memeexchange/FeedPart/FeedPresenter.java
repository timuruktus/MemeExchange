package ru.timuruktus.memeexchange.FeedPart;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.softw4re.views.InfiniteListView;

import java.util.List;

import io.realm.Realm;
import ru.timuruktus.memeexchange.Model.DataManager;
import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observer;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.realm;

@InjectViewState
public class FeedPresenter extends MvpPresenter<IFeedView> implements IFeedPresenter  {


    private DataManager dataManager;
    private FeedAdapter infiniteListView;

    public FeedPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TESTING_TAG, "onFirstViewAttach() in FeedPresenter");
        loadFeed();
    }

    @Override
    public void loadFeed(boolean showLoading){
        getViewState().showError(false);
        if(showLoading) {
            getViewState().showLoadingIndicator(true);
        }
        dataManager.loadMemesFromWeb()
                .subscribe(new Observer<List<Meme>>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        e.printStackTrace();
                        getViewState().showMessageNoInternetConnection();
                        loadFeedFromCache();
                    }

                    @Override
                    public void onNext(List<Meme> memesList) {
                        getViewState().showPosts(memesList);
                    }
                });
    }

    @Override
    public void loadFeed() {
        loadFeed(true);
    }

    @Override
    public void loadFeedFromCache() {
        dataManager.loadMemesFromCache(realm)
                .subscribe(new Observer<List<Meme>>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showLoadingIndicator(false);
                        Log.d(TESTING_TAG, "onCompleted() in loadShopsFromCache()");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        e.printStackTrace();
                        getViewState().showLoadingIndicator(false);
                        getViewState().showError(true);
                        Log.d(TESTING_TAG, "onError() in loadShopsFromCache()");
                    }

                    @Override
                    public void onNext(List<Meme> memesList) {
                        Log.d(TESTING_TAG, "onNext() in loadShopsFromCache()");
                        if (memesList.size() == 0) {
                            onError(new Throwable("Empty cache"));
                        }else {
                            getViewState().showPosts(memesList);
                        }
                    }
                });
    }

    @Override
    public void onCreateView() {
        Log.d(TESTING_TAG, "onCreateView() in feedPresenter");
        if(dataManager == null){
            dataManager = DataManager.getInstance();
        }
    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDestroyFragment(){
        Log.d(TESTING_TAG, "onDestroyFragment() in feedPresenter");
        if(dataManager != null){
            dataManager = null;
        }
    }

    @Override
    public void saveAdapter(FeedAdapter feedAdapter) {
        this.infiniteListView = feedAdapter;
    }

    @Override
    public FeedAdapter getSavedAdapter() {
        return infiniteListView;
    }


}
