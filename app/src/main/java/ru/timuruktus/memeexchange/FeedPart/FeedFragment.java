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

import com.airbnb.lottie.LottieAnimationView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.Events.OpenFragment;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.EndlessScrollListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ru.timuruktus.memeexchange.FeedPart.FeedPresenter.BUNDLE_AUTHOR;
import static ru.timuruktus.memeexchange.FeedPart.FeedPresenter.BUNDLE_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.FEED_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.LOGIN_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.Model.DataManager.DEFAULT_PAGE_SIZE;

public class FeedFragment extends MvpAppCompatFragment implements IFeedView,
        FeedAdapter.AdapterEventListener,
        SwipeRefreshLayout.OnRefreshListener{

    @Nullable @BindView(R.id.feedList) RecyclerView recyclerView;
    @Nullable @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @Nullable @BindView(R.id.loadingLayout) RelativeLayout loadingLayout;
    @Nullable @BindView(R.id.refreshIcon) ImageView refreshIcon;
    @Nullable @BindView(R.id.errorLayout) RelativeLayout errorLayout;

    @InjectPresenter
    public FeedPresenter feedPresenter;

    Unbinder unbinder;
    private Context context;
    private static final String RECYCLER_VIEW_STATE = "recyclerViewState";
    private FeedAdapter feedAdapter;
    Parcelable layoutManagerState;
    private LinearLayoutManager llm = new LinearLayoutManager(context);



    public static FeedFragment getInstance(String tag, String userId){
        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TAG, tag);
        bundle.putString(BUNDLE_AUTHOR, userId);
        feedFragment.setArguments(bundle);
        return feedFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(
                R.layout.feed_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        String newTag = getArguments().getString(BUNDLE_TAG);
        String newUser = getArguments().getString(BUNDLE_AUTHOR);
        feedPresenter.onCreateView(newTag, newUser);
        EventBus.getDefault().post(new OpenFragment(newTag));
        return view;

    }

    /**
     * Used only when user clicked on refresh button
     * For example, when user doesn't have an internet connection
     */
    @OnClick(R.id.refreshIcon)
    void onRefreshClick(){
        feedPresenter.refreshAllData(true);
    }

    /**
     * Used when user swipe down in the top of the list.
     * Refresh RecycleView listener
     */
    @Override
    public void onRefresh(){
        feedPresenter.refreshAllData(false);
    }

    /**
     * Used ONLY on RecyclerView refresh and on first view attach
     *
     * @param data - data, needed to be shown
     */
    @Override
    public void showNewPosts(ArrayList<RecyclerItem> data){
        showPosts(data);
    }

    @Override
    public void showNewUserPosts(ArrayList<RecyclerItem> data){
        showPosts(data);
    }

    private void showPosts(ArrayList<RecyclerItem> data){
        swipeContainer.setRefreshing(false);
        swipeContainer.setVisibility(VISIBLE);
        swipeContainer.setOnRefreshListener(this);

        feedAdapter = new FeedAdapter(getActivity(), data, this);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(getRecyclerViewScrollListener());
        recyclerView.setLayoutManager(llm);
    }


    /**
     * Shows an error
     *
     * @param show
     */
    @Override
    public void showError(boolean show){
        if(show){
            errorLayout.setVisibility(VISIBLE);
        } else{
            errorLayout.setVisibility(GONE);
        }
    }

    /**
     * Shows loading indicator
     *
     * @param show
     */
    @Override
    public void showLoadingIndicator(boolean show){
        if(show){
            loadingLayout.setVisibility(VISIBLE);
        } else{
            loadingLayout.setVisibility(GONE);
        }
    }

    @Override
    public void showMorePosts(ArrayList<Meme> data, int offset){
        swipeContainer.setVisibility(VISIBLE);
        feedAdapter.notifyItemRangeChanged(offset, data.size());

    }

    @Override
    public void clearRecyclerViewPool(){
        recyclerView.getRecycledViewPool().clear();
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessageNoInternetConnection(){
        Toast.makeText(context, R.string.error_loading_shops, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //If we catch an exception, it means that recyclerView is not yet created.
        try{
            outState.putParcelable(RECYCLER_VIEW_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
        //If we catch an exception, it means that recyclerView is not yet created.
        try{
            layoutManagerState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(layoutManagerState != null &&
                recyclerView != null &&
                recyclerView.getLayoutManager() != null){
            recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerState);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(layoutManagerState != null && recyclerView != null){
            layoutManagerState = recyclerView.getLayoutManager().onSaveInstanceState();
        }
    }

    @Override
    public void onLiked(Meme meme){

    }

    @Override
    public void onSubscribe(User user){

    }

    public EndlessScrollListener getRecyclerViewScrollListener(){
        return new EndlessScrollListener(llm){
            @Override
            public void onLoadMore(int offset){
                feedPresenter.loadMoreFeed(offset, DEFAULT_PAGE_SIZE);
            }
        };
    }

}
