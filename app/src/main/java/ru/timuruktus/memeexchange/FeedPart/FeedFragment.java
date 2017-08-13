package ru.timuruktus.memeexchange.FeedPart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.R;

public class FeedFragment extends Fragment implements IFeedView {

    private View rootView;
    private IFeedPresenter feedPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(
                R.layout.feed_fragment, container, false);

        feedPresenter = new FeedPresenter(this);
        feedPresenter.loadFeed();

        return rootView;
    }

    @Override
    public void showPosts(List<Meme> memes) {

    }

    @Override
    public void showError(boolean show) {

    }

    @Override
    public void showProgressIndicator(boolean show) {

    }

    @Override
    public void showMessageNoInternetConnection() {

    }
}
