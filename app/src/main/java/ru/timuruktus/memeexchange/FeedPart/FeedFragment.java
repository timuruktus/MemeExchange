package ru.timuruktus.memeexchange.FeedPart;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.CustomFeedListenerListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.Model.DataManager.DEFAULT_PAGE_SIZE;

public class FeedFragment extends MvpAppCompatFragment implements IFeedView,
        CustomFeedListenerListener.ScrollEventListener, FeedAdapter.PostEventListener{

    @Nullable @BindView(R.id.feedList) RecyclerView feedList;
    @Nullable @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @Nullable @BindView(R.id.progressBar) ProgressBar progressBar;
    @Nullable @BindView(R.id.loadingLayout) RelativeLayout loadingLayout;
    @Nullable @BindView(R.id.errorIcon) ImageView errorIcon;
    @Nullable @BindView(R.id.errorText) TextView errorText;
    @Nullable @BindView(R.id.refreshIcon) ImageView refreshIcon;
    @Nullable @BindView(R.id.errorLayout) RelativeLayout errorLayout;

    @InjectPresenter
    public FeedPresenter feedPresenter;

    Unbinder unbinder;
    private Context context;
    private static final String RECYCLER_VIEW_STATE = "recyclerViewPosition";
    private FeedAdapter feedAdapter;
    private static ArrayList<Meme> memeData = new ArrayList<>();
    Parcelable layoutManagerState;
    private static int offset;
    private LinearLayoutManager llm = new LinearLayoutManager(context);
    private CustomFeedListenerListener listener = new CustomFeedListenerListener(this);

    public static String currentTag;
    public static final String BUNDLE_TAG = "bundleTag";
    public static final String NEWEST_FEED_TAG = "newestFeedTag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        feedPresenter.onCreateView();
        View view = inflater.inflate(
                R.layout.feed_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();
        feedList.setHasFixedSize(false);
        // TODO: При новом тэге обнулям данные (memeData) и оффсет

        return view;

    }

    @OnClick(R.id.refreshIcon)
    void onRefreshClick(){
        memeData.clear();
        feedPresenter.loadFeed(0);
    }

    @Override
    public void showPosts(List<Meme> memes){
//        memeData.clear();
        Log.d(TESTING_TAG, "showPosts() in FeedFragment.");


        if(memeData.size() == 0){
            memeData.addAll(memes);
        }

        swipeContainer.setRefreshing(false);
        swipeContainer.setVisibility(VISIBLE);
        loadingLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);

        swipeContainer.setOnRefreshListener(listener);
//        feedList.addOnScrollListener(listener);


        feedAdapter = new FeedAdapter(getActivity(), memeData, this);
        feedAdapter.setNeededToRefresh(true);
        feedList.setAdapter(feedAdapter);
        feedList.setLayoutManager(llm);
    }

    @Override
    public void showError(boolean show){
        if(show){
            swipeContainer.setVisibility(GONE);
            loadingLayout.setVisibility(GONE);
            errorLayout.setVisibility(VISIBLE);
        } else{
            errorLayout.setVisibility(GONE);
        }
    }


    @Override
    public void showLoadingIndicator(boolean show){
        if(show){
            swipeContainer.setVisibility(GONE);
            loadingLayout.setVisibility(VISIBLE);
            errorLayout.setVisibility(GONE);
        } else{
            loadingLayout.setVisibility(GONE);
        }
    }

    @Override
    public void showMorePosts(List<Meme> memeList, int offset){
        Log.d(TESTING_TAG, "showMorePosts() in FeedFragment. memeSize = " + memeList.size() + " offset = " + offset);
        swipeContainer.setVisibility(VISIBLE);
        loadingLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);
        memeData.addAll(memeList);
        FeedFragment.offset = offset;
        feedList.getRecycledViewPool().clear();
        feedAdapter.notifyItemRangeChanged(offset, memeList.size());
        if(memeList.size() < DEFAULT_PAGE_SIZE){
            feedAdapter.setNeededToRefresh(false);
        }else{
            feedAdapter.setNeededToRefresh(true);
        }

    }

    @Override
    public void showMessageNoInternetConnection(){
        Toast.makeText(context, R.string.error_loading_shops, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        refreshIcon = null;
        errorLayout = null;
        loadingLayout = null;
        unbinder.unbind();
        feedPresenter.onDestroyView();
    }


    @Override
    public void onRefresh(){
        memeData.clear();
        feedPresenter.loadFeed(false, 0);
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        try{
            outState.putParcelable(RECYCLER_VIEW_STATE, feedList.getLayoutManager().onSaveInstanceState());
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
        try{
            layoutManagerState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(layoutManagerState != null){
            feedList.getLayoutManager().onRestoreInstanceState(layoutManagerState);
        }
    }


//    @Override
//    public void onLoadMore(int page, int offset){
//        Log.d(TESTING_TAG, "onLoadMore() in FeedFragment. Page = " + page + " offset = " + offset);
//        feedPresenter.loadMoreFeed(offset, DEFAULT_PAGE_SIZE);
//    }

    @Override
    public void onLiked(Meme meme){

    }

    @Override
    public void onLoadMore(int offset){
        feedPresenter.loadMoreFeed(offset, DEFAULT_PAGE_SIZE);
    }
}
