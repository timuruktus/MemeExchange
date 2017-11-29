package ru.timuruktus.memeexchange.NewPostPart;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.yalantis.ucrop.UCrop;

import ru.timuruktus.memeexchange.MainPart.GlideApp;
import ru.timuruktus.memeexchange.MainPart.MyApp;

import static android.app.Activity.RESULT_OK;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.NEW_POST_FRAGMENT_TAG;
import static ru.timuruktus.memeexchange.NewPostPart.NewPostFragment.GALLERY_REQUEST;

@InjectViewState
public class NewPostPresenter extends MvpPresenter<INewPostView> implements INewPostPresenter, SettingsDialogFragment.SettingsDialogFragmentListener{

    @Override
    protected void onFirstViewAttach(){
        super.onFirstViewAttach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case UCrop.REQUEST_CROP:
                if(resultCode == RESULT_OK){
                    Uri resultUri = UCrop.getOutput(data);
                    getViewState().showImage(resultUri);

                }else if(resultCode == UCrop.RESULT_ERROR){
                    final Throwable cropError = UCrop.getError(data);
                    cropError.printStackTrace();
                }
                break;

            case GALLERY_REQUEST:

                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    getViewState().launchUCropActivity(selectedImage);
                }

                break;
        }
    }

    @Override
    public void onSendMemeButtonClicked(){

    }

    @Override
    public void onTextSettingsClicked(int gravity, int textSize, int textShadow){
        SettingsDialogFragment dialog = new SettingsDialogFragment();
        SettingsDialogFragmentOptions options =
                new SettingsDialogFragmentOptions(textSize, gravity, textShadow);
        dialog.setOptions(options);
        dialog.setListener(this);
        getViewState().showBottomDialog(dialog);
    }

    @Override
    public void onViewCreated(){
        if(MyApp.getSettings().isFragmentFirstOpen(NEW_POST_FRAGMENT_TAG)){
            getViewState().showFirstOpenHint();
        }
    }


    @Override
    public void onOptionsChanged(SettingsDialogFragmentOptions options){
        getViewState().onOptionsChanged(options);
    }
}
