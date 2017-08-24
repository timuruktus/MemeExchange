package ru.timuruktus.memeexchange.FeedPart;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.softw4re.views.InfiniteListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.EndlessScrollListener;

import static android.view.View.VISIBLE;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

public class FeedFragment extends MvpAppCompatFragment implements IFeedView {

    @Nullable @BindView(R.id.feedListView) ListView feedListView;
    @Nullable @BindView(R.id.progressBar) ProgressBar progressBar;
    @Nullable @BindView(R.id.loadingLayout) RelativeLayout loadingLayout;
    @Nullable @BindView(R.id.errorIcon) ImageView errorIcon;
    @Nullable @BindView(R.id.errorText) TextView errorText;
    @Nullable @BindView(R.id.refreshIcon) ImageView refreshIcon;
    @Nullable @BindView(R.id.errorLayout) RelativeLayout errorLayout;

    @InjectPresenter
    public FeedPresenter feedPresenter;

    Unbinder unbinder;
    private View rootView;
    private Context context;
    private static int listViewPosition;
    private static final String LIST_VIEW_POSITION = "key";

    public static final String FEED_FRAGMENT_TAG = "feedFragmentTag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        feedPresenter.onCreateView();
        View view = inflater.inflate(
                R.layout.feed_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView = view;
        context = view.getContext();
//        feedPresenter.loadFeed();
    }

    @OnClick(R.id.refreshIcon)
    void onRefreshClick() {
        feedPresenter.loadFeed();
    }

    @Override
    public void showPosts(List<Meme> memes) {
        feedListView.setVisibility(VISIBLE);
        FeedAdapter feedAdapter = new FeedAdapter(getActivity(), R.layout.meme_layout, (ArrayList<Meme>) memes, this);
        feedListView.stopLoading();
        feedListView.setAdapter(feedAdapter);
    }

    private void configureListView(){
        feedListView.setOnScrollListener(new EndlessScrollListener(){

            // Defines the process for actually loading more data based on page
            // Returns true if more data is being loaded; returns false if there is no more data to load.
            @Override
            public boolean onLoadMore(int page, int totalItemsCount){
                return false;
            }
        });
    }

    void onNewLoadRequired(){

    }

    @Override
    public void showError(boolean show) {
        if (show) {
            errorLayout.setVisibility(VISIBLE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void showLoadingIndicator(boolean show) {
        if(show) {
            loadingLayout.setVisibility(VISIBLE);
        }else {
            loadingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessageNoInternetConnection() {
        Toast.makeText(context, R.string.error_loading_shops, Toast.LENGTH_LONG).show();
    }

    @Override
    public void adapterRefreshCall() {
        feedPresenter.loadFeed(false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
//        if(infiniteListView != null){
//            outState.putInt(LIST_VIEW_POSITION, infiniteListView.getScrollY());
//            Log.d(TESTING_TAG, "onSaveInstanceState() in FeedFragment. infiniteListView.getScrollY() = " + infiniteListView.getScrollY());
//        }else{
//            outState.putInt(LIST_VIEW_POSITION, 0);
//            Log.d(TESTING_TAG, "onSaveInstanceState() in FeedFragment. infiniteListView == null");
//        }
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
//        if(savedInstanceState != null){
//            listViewPosition = savedInstanceState.getInt(LIST_VIEW_POSITION);
//            Log.d(TESTING_TAG, "onViewStateRestored() in FeedFragment. listViewPosition = " + listViewPosition);
//        }else{
//            listViewPosition = 0;
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refreshIcon = null;
        errorLayout = null;
        loadingLayout = null;
        unbinder.unbind();
        feedPresenter.onDestroyView();
    }
}
