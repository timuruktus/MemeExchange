package ru.timuruktus.memeexchange.NewPostPart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.Events.OpenFragment;
import ru.timuruktus.memeexchange.R;

import static android.app.Activity.RESULT_OK;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.NEW_POST_FRAGMENT_TAG;

public class NewPostFragment extends MvpAppCompatFragment implements INewPostView{


    private Context context;
    @InjectPresenter
    public NewPostPresenter presenter;
    Unbinder unbinder;

    public static NewPostFragment getInstance(){
        return new NewPostFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(
                R.layout.new_meme_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();
        EventBus.getDefault().post(new OpenFragment(NEW_POST_FRAGMENT_TAG));
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }
}
