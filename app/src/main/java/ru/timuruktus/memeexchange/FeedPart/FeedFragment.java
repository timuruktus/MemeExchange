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
import ru.timuruktus.memeexchange.Utils.CustomEndlessScrollListener;
import ru.timuruktus.memeexchange.Utils.EndlessScrollListener;

import static android.view.View.VISIBLE;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.Model.DataManager.DEFAULT_PAGE_SIZE;

public class FeedFragment extends MvpAppCompatFragment implements IFeedView,
        CustomEndlessScrollListener.ScrollEventListener{

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
    private Context context;
    private static int listViewPosition;
    private static final String LIST_VIEW_POSITION = "key";
    private FeedAdapter feedAdapter;
    private static ArrayList<Meme> memeData;
    private static int offset;

    public static final String FEED_FRAGMENT_TAG = "feedFragmentTag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        feedPresenter.onCreateView();
        View view = inflater.inflate(
                R.layout.feed_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();
//        feedListView.
        return view;

    }

    @OnClick(R.id.refreshIcon)
    void onRefreshClick() {
        feedPresenter.loadFeed(0);
    }

    @Override
    public void showPosts(List<Meme> memes, int offset) {
        if(offset <= DEFAULT_PAGE_SIZE){
            // TODO: ПЕРЕРАБОТАТЬ ЗДЕСЬ ВСЕ НАХЕР!!!
        }
        Log.d(TESTING_TAG, "showPosts() in FeedFragment.");
        feedListView.setVisibility(VISIBLE);
        feedListView.setOnScrollListener(new CustomEndlessScrollListener(this));
        if(offset == 0){
            memeData = new ArrayList<>();
            FeedFragment.offset = 0;
        }else{
            FeedFragment.offset += offset;
        }
        memeData.addAll(memes);
        if(feedAdapter == null){
            Log.d(TESTING_TAG, "showPosts() in FeedFragment. feedAdapter == null");
            feedAdapter = new FeedAdapter(getActivity(), memeData);
            feedListView.setAdapter(feedAdapter);
        }else{
            Log.d(TESTING_TAG, "showPosts() in FeedFragment. feedAdapter != null");
            feedAdapter.notifyDataSetChanged();
        }
        //TODO: осмыслить

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
    public void onPause() {
        super.onPause();
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

    // Defines the process for actually loading more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load.
    @Override
    public boolean onLoadMore(int page, int offset){
        Log.d(TESTING_TAG, "onLoadMore() in FeedFragment. Page = " + page + " offset = " + offset);
        feedPresenter.loadFeed(offset);
        return false;
    }
}
