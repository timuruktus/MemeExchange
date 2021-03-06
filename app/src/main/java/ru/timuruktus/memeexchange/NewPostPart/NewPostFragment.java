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
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.timuruktus.memeexchange.Events.OpenFragment;
import ru.timuruktus.memeexchange.MainPart.GlideApp;
import ru.timuruktus.memeexchange.MainPart.MyApp;
import ru.timuruktus.memeexchange.R;
import ru.timuruktus.memeexchange.Utils.Converter;
import ru.timuruktus.memeexchange.Utils.WindowMethods;

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
    @BindColor(R.color.colorPrimary) int primaryColor;
    @BindColor(R.color.colorAccent) int accentColor;
    @BindColor(R.color.black) int blackColor;
    @BindColor(R.color.transparentGray) int textBackground;
    @BindView(R.id.savedMemesButton) ImageView savedMemesButton;

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
    UCrop.Options ucropOptions;

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
        configureUcropOptions();
        return rootView;
    }

    private void configureUcropOptions(){
        ucropOptions = new UCrop.Options();
        ucropOptions.setActiveWidgetColor(accentColor);
        ucropOptions.setStatusBarColor(accentColor);
        ucropOptions.setToolbarColor(accentColor);
        ucropOptions.setToolbarTitle(" ");
    }


    private void setListeners(){
        topMemeText.setOnFocusChangeListener((view, hasFocus) -> {
            if(view == null || topTextSettings == null){
                return;
            }
            if(hasFocus){
                topTextSettings.setVisibility(View.VISIBLE);
            }else{
                topTextSettings.setVisibility(View.GONE);
            }
        });

        bottomMemeText.setOnFocusChangeListener((view, hasFocus) -> {
            if(view == null || bottomTextSettings == null){
                return;
            }
            if(hasFocus){
                bottomTextSettings.setVisibility(View.VISIBLE);
            }else{
                bottomTextSettings.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        presenter.onActivityResult(requestCode, resultCode, data);
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
        showImage(null);
        setEditTextBackground(textBackground);

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
                    .setAction(R.string.to_settings, snackbarOnClickListener).show();
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
        setEditTextBackground(TRANSPARENT);
        memeImageLayout.setDrawingCacheEnabled(true);
        memeImageLayout.buildDrawingCache();
        memeBitmap = memeImageLayout.getDrawingCache();
    }

    public void setEditTextBackground(int color){
        topMemeText.setBackgroundColor(color);
        bottomMemeText.setBackgroundColor(color);
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
    public void launchUCropActivity(Uri selectedImage){
        Uri destinationUri = Uri.parse(context.getFilesDir().getAbsolutePath() + "/" + System.currentTimeMillis());

        UCrop.of(selectedImage, destinationUri)
                .withAspectRatio(1, 1)
                .withOptions(ucropOptions)
                .start(context, this);
    }

    @Override
    public void clearMemeTextFocuses(){
        topMemeText.clearFocus();
        bottomMemeText.clearFocus();
    }

    @Override
    public void showFirstOpenHint(){
        View view = getActivity().findViewById(R.id.container);
        int text = R.string.first_new_post_open;
        int buttonText = R.string.understand;
        int duration = Snackbar.LENGTH_INDEFINITE;
        Snackbar snackbar = Snackbar.make(view, text, duration);
        snackbar.setAction(buttonText, v -> {
            snackbar.dismiss();
            MyApp.getSettings().setFragmentFirstOpen(NEW_POST_FRAGMENT_TAG, false);
        });
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blue));
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(50);
        snackbar.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated();
    }

    //    @Override
//    public void showAllTags(ArrayList<Label> tags){
//        removeAllTags();
//        for(Label label : tags){
//            tagsView.addLabel(label.getText());
//        }
//
//    }

    @Override
    public void showAllTags(ArrayList<String> tags){
        removeAllTags();
        for(String text : tags){
            tagsView.addLabel(text);
        }

    }

    //null if we want to clear image
    @Override
    public void showImage(Uri uri){
        GlideApp.with(context)
                .load(uri)
                .centerCrop()
                .into(memeImage);
    }

    @Override
    public void showBottomDialog(SettingsDialogFragment dialog){
        dialog.show(getChildFragmentManager(), dialog.getTag());
    }

    @OnClick(R.id.topTextSettings)
    public void onTopTextSettingsClicked(){
        WindowMethods.hideKeyboard(getActivity());
        int gravity = topMemeText.getGravity();
        int textSize = (int) Converter.convertPixelsToDp(topMemeText.getTextSize(), context);
        int textShadow = (int) topMemeText.getShadowRadius();
        Log.d(TESTING_TAG, "onTopTextSettingsClicked() Gravity = " + gravity);
        presenter.onTextSettingsClicked(gravity, textSize, textShadow);
    }

    @OnClick(R.id.bottomTextSettings)
    public void onBottomTextSettingsClicked(){
        WindowMethods.hideKeyboard(getActivity());
        int gravity = bottomMemeText.getGravity();
        int textSize = (int) Converter.convertPixelsToDp(bottomMemeText.getTextSize(), context);
        int textShadow = (int) bottomMemeText.getShadowRadius();
        presenter.onTextSettingsClicked(gravity, textSize, textShadow);
    }

    @Override
    public void onOptionsChanged(SettingsDialogFragmentOptions options){
        int textSize = options.getCurrentSize();
        int textShadow = options.getCurrentShadow();
        int textGravity = options.getCurrentGravity();
        if(topMemeText.hasFocus()){
            topMemeText.setTextSize(textSize);
            topMemeText.setShadowLayer(textShadow, 1, 1, blackColor);
            topMemeText.setGravity(textGravity);
        }else if(bottomMemeText.hasFocus()){
            bottomMemeText.setTextSize(textSize);
            bottomMemeText.setShadowLayer(textShadow, 1, 1, blackColor);
            bottomMemeText.setGravity(textGravity);
        }
    }

    @Override
    public void onStop(){
        super.onStop();

    }

    @OnClick(R.id.savedMemesButton)
    public void onViewClicked(){

    }
}
