package ru.timuruktus.memeexchange.AccountPart;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.Events.OpenFragment;
import ru.timuruktus.memeexchange.R;

import static ru.timuruktus.memeexchange.FeedPart.FeedPresenter.BUNDLE_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.FEED_FRAGMENT_TAG;

public class AccountFragment extends MvpAppCompatFragment implements IAccountView{

    public static final String LATEST_USER_MEMES = "latestUserMemes";
    private static final String BUNDLE_TAG = "tag";
    private static final String BUNDLE_AUTHOR = "author";

    @InjectPresenter
    public AccountPresenter feedPresenter;
    Unbinder unbinder;
    private Context context;

    Parcelable layoutManagerState;
    private LinearLayoutManager llm = new LinearLayoutManager(context);
    private static final String RECYCLER_VIEW_STATE = "recyclerViewState";


    public static AccountFragment getInstance(String tag, String authorId){
        AccountFragment accountFragment = new AccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TAG, tag);
        bundle.putString(BUNDLE_AUTHOR, authorId);
        accountFragment.setArguments(bundle);
        return accountFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(
                R.layout.feed_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();
        recyclerView.setHasFixedSize(false);
        String newTag = getArguments().getString(BUNDLE_TAG);
        String userId = getArguments().getString(BUNDLE_AUTHOR);
        feedPresenter.onCreateView(newTag, userId);
        EventBus.getDefault().post(new OpenFragment(FEED_FRAGMENT_TAG));
        return view;

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }
}
