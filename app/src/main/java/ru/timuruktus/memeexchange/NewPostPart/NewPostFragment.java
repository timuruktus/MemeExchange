package ru.timuruktus.memeexchange.NewPostPart;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.Events.OpenFragment;
import ru.timuruktus.memeexchange.MainPart.GlideApp;
import ru.timuruktus.memeexchange.MainPart.MyApp;
import ru.timuruktus.memeexchange.R;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Color.TRANSPARENT;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainPresenter.NEW_POST_FRAGMENT_TAG;

public class NewPostFragment extends MvpAppCompatFragment implements INewPostView{


    @BindView(R.id.memeImage) ImageView memeImage;
    @BindView(R.id.backArrow) TextView backArrow;
    @BindView(R.id.backTextView) ImageView backTextView;
    @BindView(R.id.memeEditText) EditText memeEditText;
    @BindView(R.id.topMemeText) EditText topMemeText;
    @BindView(R.id.bottomMemeText) EditText bottomMemeText;
    @BindView(R.id.addTagsText) TextView addTagsText;
    @BindView(R.id.addTagsHint) TextView addTagsHint;
    @BindView(R.id.label_view) AutoLabelUI tagsView;
    @BindView(R.id.addTagButton) Button addTagButton;
    @BindView(R.id.tagEditText) EditText tagEditText;
    @BindView(R.id.sendMemeButton) TextView sendMemeButton;
    @BindView(R.id.clearImageButton) Button clearImageButton;
    @BindView(R.id.memeImageLayout) ConstraintLayout memeImageLayout;
    @BindString(R.string.sending_error) String sendingError;
    @BindString(R.string.success_sending) String successSending;
    @BindString(R.string.too_many_tags) String tooManyTags;
    @BindView(R.id.topTextSettings) ImageView topTextSettings;
    @BindView(R.id.bottomTextSettings) ImageView bottomTextSettings;
    @BindView(R.id.chooseImageButton) Button chooseImageButton;
    private Context context;
    @InjectPresenter
    public NewPostPresenter presenter;
    Unbinder unbinder;
    private Uri sourceUri;
    static final int GALLERY_REQUEST = 1;
    public static final int PERMISSION_REQUEST_STORAGE = 0;
    public static final int PERMISSION_REQUEST_CODE = 1;
    private View rootView;
    private Bitmap memeBitmap;

    public static NewPostFragment getInstance(){
        return new NewPostFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        rootView = inflater.inflate(
                R.layout.new_meme_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = rootView.getContext();
        EventBus.getDefault().post(new OpenFragment(NEW_POST_FRAGMENT_TAG));
        setListeners();
        return rootView;
    }


    private void setListeners(){
        topMemeText.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus){
                topTextSettings.setVisibility(View.VISIBLE);
            }else{
                topTextSettings.setVisibility(View.GONE);
            }
        });

        bottomMemeText.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus){
                topTextSettings.setVisibility(View.VISIBLE);
            }else{
                topTextSettings.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case UCrop.REQUEST_CROP:
                if(resultCode == RESULT_OK){
                    Uri resultUri = UCrop.getOutput(data);
                    GlideApp.with(context)
                            .load(resultUri)
                            .centerCrop()
                            .into(memeImage);
                }else if(resultCode == UCrop.RESULT_ERROR){
                    final Throwable cropError = UCrop.getError(data);
                    cropError.printStackTrace();
                }
                break;

            case GALLERY_REQUEST:
                Uri destinationUri = Uri.parse(context.getFilesDir().getAbsolutePath() + "/" + System.currentTimeMillis());
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    UCrop.of(selectedImage, destinationUri)
                            .withAspectRatio(1, 1)
                            .start(context, this);
                }

                break;
        }

    }

    private boolean checkPermissions(){
        int readStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return readStoragePermission == PackageManager.PERMISSION_GRANTED && writeStoragePermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.backArrow)
    public void onBackArrowClicked(){
        MyApp.INSTANCE.getRouter().exit();
    }

    @OnClick(R.id.backTextView)
    public void onBackTextViewClicked(){
        MyApp.INSTANCE.getRouter().exit();
    }

    @OnClick(R.id.addTagButton)
    public void onAddTagButtonClicked(){
        String text = tagEditText.getText().toString();
        tagsView.addLabel(text);
        tagEditText.setText("");
    }

    @OnClick(R.id.sendMemeButton)
    public void onSendMemeButtonClicked(){
        //TODO: Delete code below
        prepareImageForSending();
    }

    @OnClick(R.id.clearImageButton)
    public void onClearImageButtonClicked(){
        clearTextLabels();
        clearImage();

    }

    @OnClick(R.id.chooseImageButton)
    public void onChooseImageButtonClicked(){
        Log.d(TESTING_TAG, "permissions granted = " + checkPermissions());
        if(checkPermissions()){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }else{
            Snackbar.make(rootView, R.string.grant_permission, Snackbar.LENGTH_LONG)
                    .setAction(R.string.to_settings, snackbarOnClickListener);
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    PERMISSION_REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults){
        switch(requestCode){
            case PERMISSION_REQUEST_STORAGE:{
                // If request is cancelled, the result arrays are empty.
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                }else{
                    Snackbar.make(rootView, R.string.grant_permission, Snackbar.LENGTH_LONG)
                            .setAction(R.string.to_settings, snackbarOnClickListener);
                }
                return;
            }

        }
    }

    View.OnClickListener snackbarOnClickListener = view -> {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    };

    @Override
    public void clearTextLabels(){
        topMemeText.setText("");
        bottomMemeText.setText("");
    }

    @Override
    public void clearImage(){
        Bitmap bitmap = null;
        GlideApp.with(context)
                .load(bitmap)
                .centerCrop()
                .into(memeImage);
    }

    @Override
    public void showLoading(boolean show){

    }

    @Override
    public void showLoadingError(){
        MyApp.INSTANCE.getRouter().showSystemMessage(sendingError);
    }

    @Override
    public void showLoadingSuccess(){
        MyApp.INSTANCE.getRouter().showSystemMessage(successSending);
    }

    @Override
    public void prepareImageForSending(){
        topMemeText.setBackgroundColor(TRANSPARENT);
        bottomMemeText.setBackgroundColor(TRANSPARENT);
        memeImageLayout.setDrawingCacheEnabled(true);
        memeImageLayout.buildDrawingCache();
        memeBitmap = memeImageLayout.getDrawingCache();
    }

    @Override
    public void tooManyTagsError(){
        MyApp.INSTANCE.getRouter().showSystemMessage(tooManyTags);
    }

    @Override
    public void removeAllTags(){
        tagsView.clear();
    }

    @Override
    public void showAllTags(ArrayList<Label> tags){
        removeAllTags();
        for(Label label : tags){
            tagsView.addLabel(label.getText());
        }

    }

    @OnClick(R.id.topTextSettings)
    public void onTopTextSettingsClicked(){

    }

    @OnClick(R.id.bottomTextSettings)
    public void onBottomTextSettingsClicked(){

    }
}
