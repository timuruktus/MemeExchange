package ru.timuruktus.memeexchange.NewPostPart;

import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.dpizarro.autolabel.library.Label;

import java.util.ArrayList;

import ru.timuruktus.memeexchange.POJO.RecyclerItem;

public interface INewPostView extends MvpView{

    @StateStrategyType(OneExecutionStateStrategy.class)
    void clearTextLabels();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showLoading(boolean show);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showLoadingError();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showLoadingSuccess();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void prepareImageForSending();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void tooManyTagsError();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void removeAllTags();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void launchUCropActivity(Uri selectedImage);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void clearMemeTextFocuses();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showFirstOpenHint();

    @StateStrategyType(SingleStateStrategy.class)
    void showAllTags(ArrayList<String> tags);

    @StateStrategyType(SingleStateStrategy.class)
    void showImage(Uri uri);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showBottomDialog(SettingsDialogFragment dialog);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onOptionsChanged(SettingsDialogFragmentOptions options);
}
