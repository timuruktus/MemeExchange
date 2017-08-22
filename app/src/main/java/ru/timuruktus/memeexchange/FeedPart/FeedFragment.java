package ru.timuruktus.memeexchange.FeedPart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.softw4re.views.InfiniteListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.R;

import static android.view.View.VISIBLE;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

public class FeedFragment extends MvpAppCompatFragment implements IFeedView {

    @Nullable @BindView(R.id.infiniteListView) InfiniteListView infiniteListView;
    @Nullable @BindView(R.id.progressBar) ProgressBar progressBar;
    @Nullable @BindView(R.id.loadingLayout) RelativeLayout loadingLayout;
    @Nullable @BindView(R.id.errorIcon) ImageView errorIcon;
    @Nullable @BindView(R.id.errorText) TextView errorText;
    @Nullable @BindView(R.id.refreshIcon) ImageView refreshIcon;
    @Nullable @BindView(R.id.errorLayout) RelativeLayout errorLayout;

    Unbinder unbinder;
    private View rootView;
    private Context context;
    @InjectPresenter
    public FeedPresenter feedPresenter;
    public static final String FEED_FRAGMENT_TAG = "feedFragmentTag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        feedPresenter.onCreateView();
        Log.d(TESTING_TAG, "onCreateView() in FeedFragment");
        View view = inflater.inflate(
                R.layout.feed_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TESTING_TAG, "onViewCreated() in FeedFragment");
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
        infiniteListView.setVisibility(VISIBLE);
        Log.d(TESTING_TAG, "showPosts() in FeedFragment");
        FeedAdapter shopsAdapter = new FeedAdapter(getActivity(), R.layout.meme_layout, (ArrayList<Meme>) memes, this);
        infiniteListView.stopLoading();
        infiniteListView.setKeepScreenOn(true);
        infiniteListView.setAdapter(shopsAdapter);
    }

    @Override
    public void showError(boolean show) {
        Log.d(TESTING_TAG, "showError() in FeedFragment");
        if (show) {
            errorLayout.setVisibility(VISIBLE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void showLoadingIndicator(boolean show) {
        Log.d(TESTING_TAG, "showLoadingIndicator() in FeedFragment");
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

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
