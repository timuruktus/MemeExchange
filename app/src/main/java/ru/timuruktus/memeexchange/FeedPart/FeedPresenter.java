package ru.timuruktus.memeexchange.FeedPart;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.timuruktus.memeexchange.MainPart.MyApp;
import ru.timuruktus.memeexchange.Model.MemeDataManager;
import ru.timuruktus.memeexchange.POJO.Footer;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;
import ru.timuruktus.memeexchange.POJO.User;
import rx.Observable;
import rx.Observer;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.FEED_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.Model.MemeDataManager.PAGE_SIZE;

@InjectViewState
public class FeedPresenter extends MvpPresenter<IFeedView> implements IFeedPresenter{


    private MemeDataManager memeDataManager;
    private static ArrayList<RecyclerItem> posts = new ArrayList<>();
    private static String currentTag;
    private static String currentUser;
    public static final String BUNDLE_TAG = "bundleTag";
    public static final String BUNDLE_AUTHOR = "author";
    private boolean latestInformationHasCome = false;

    public FeedPresenter(){
    }

    @Override
    protected void onFirstViewAttach(){
        super.onFirstViewAttach();

    }

    @Override
    public void loadFeed(boolean showLoading, int offset){
        loadFeed(showLoading, offset, PAGE_SIZE);
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
            observable = memeDataManager.loadMemesFromWeb(pageSize, offset);
        }else{
            observable = memeDataManager.loadUserPosts(userId, pageSize, offset);
        }
        observable.subscribe(new Observer<List<RecyclerItem>>(){
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
                    public void onNext(List<RecyclerItem> data){
                        removeFooter();
                        posts.addAll(data);
                        if(posts.size() == 0){
                            getViewState().showError(true);
                        }else{
                            configureFooter(data.size());
                        }

                        getViewState().showNewPosts(getNewestPosts());
                    }
                });
    }

    @Override
    public void loadMoreFeed(int offset, int pageSize){
        if(latestInformationHasCome) return;
        getViewState().showLoadingIndicator(false);
        memeDataManager.loadMemesFromWeb(pageSize, offset)
                .subscribe(new Observer<List<Meme>>(){
                    @Override
                    public void onCompleted(){
                    }

                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Meme> memesList){
                        if(memesList.size() < 10){
                            latestInformationHasCome = true;
                        }
                        removeFooter();
                        posts.addAll(memesList);
                        configureFooter(memesList.size());
                        getViewState().showMorePosts((ArrayList<Meme>) memesList, offset);
                    }
                });
    }


    @Override
    public void loadFeedFromCache(){
        memeDataManager.loadMemesFromCache()
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

    private void configureFooter(int dataSize){
        if(dataSize >= PAGE_SIZE){
            addFooter();
        }else{
            removeFooter();
        }
    }

    @Override
    public void onCreateView(String newTag, String newUser){
        if(MyApp.getSettings().isFragmentFirstOpen(FEED_FRAGMENT_TAG)){
            getViewState().showFirstOpenHint();
        }

        if(memeDataManager == null){
            memeDataManager = MemeDataManager.getInstance();
        }

        //First Open
        if(currentTag == null){
            loadFeed(true, 0);
        }

        //User should be shown after usual posts
        if(currentUser == null && newUser != null){
            loadFeed(true, 0, PAGE_SIZE, newUser);
        }

        //New tag
        if(currentTag != null && !currentTag.equals(newTag)){
            loadFeed(true, 0);
        }
        //New User
        if(currentUser != null && !currentUser.equals(newUser)){
            loadFeed(true, 0, PAGE_SIZE, newUser);
        }
        currentTag = newTag;
        currentUser = newUser;
    }

    private void addFooter(){
        try{
            if(posts.get(posts.size() - 1) instanceof Footer){
                Log.d(DEFAULT_TAG, "Footer already added");
            }else{
                posts.add(new Footer());
            }
        }catch(NullPointerException ex){
            Log.d(DEFAULT_TAG, "posts is null");
            ex.printStackTrace();
        }
    }

    private void removeFooter(){
        try{
            if(posts.size() != 0 && posts.get(posts.size() - 1) instanceof Footer){
                posts.remove(posts.size() - 1);
            }else{
                Log.d(DEFAULT_TAG, "No footer added to posts");
            }
        }catch(NullPointerException ex){
            Log.d(DEFAULT_TAG, "posts is null");
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroyFragment(){
        if(memeDataManager != null){
            memeDataManager = null;
        }
    }

    @Override
    public void refreshAllData(boolean showLoading){
        getViewState().clearRecyclerViewPool();
        latestInformationHasCome = false;
        loadFeed(showLoading, 0);
    }

    @Override
    public ArrayList<RecyclerItem> getNewestPosts(){
        return posts;
    }

    @Override
    public void onResume(){
        ArrayList<RecyclerItem> posts = getNewestPosts();
        if(posts != null && posts.size() != 0){
            getViewState().showNewPosts(getNewestPosts());
        }
    }

    @Override
    public void onMemeLiked(Meme meme){

    }

    @Override
    public void onSubscribe(User user){

    }

}
