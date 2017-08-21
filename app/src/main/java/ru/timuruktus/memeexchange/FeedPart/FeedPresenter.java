package ru.timuruktus.memeexchange.FeedPart;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.realm.Realm;
import ru.timuruktus.memeexchange.Model.DataManager;
import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observer;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

@InjectViewState
public class FeedPresenter extends MvpPresenter<IFeedView> implements IFeedPresenter  {


    private DataManager dataManager;
    private Realm realm;

    public FeedPresenter(IFeedView feedView) {
        dataManager = DataManager.getInstance();
    }

    @Override
    public void loadFeed() {
        getViewState().showLoadingIndicator(true);
        dataManager.loadMemesFromWeb()
                .subscribe(new Observer<List<Meme>>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        e.printStackTrace();
                        Log.d(TESTING_TAG, "onError() in loadShops()");
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
    public void loadFeedFromCache() {
        dataManager.loadMemesFromCache(realm)
                .subscribe(new Observer<List<Meme>>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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
        if(realm == null){
            realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void onDestroyView() {
        if(realm != null){
            realm.close();
        }
    }


}
