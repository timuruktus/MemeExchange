package ru.timuruktus.memeexchange.NewPostPart;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;

import ru.timuruktus.memeexchange.R;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

public class SettingsDialogFragment extends BottomSheetDialogFragment{

    private SettingsDialogFragmentOptions options;
    public SettingsDialogFragmentListener listener;
    private SeekBar textSizeSeekBar;
    private SeekBar centeringSeekBar;
    private SeekBar shadowSizeSeekBar;
    private TextView currentSize;
    private TextView currentShadow;
    public static final String OPTIONS_SERIALIZABLE_KEY = "optionsKey";
    public static final String LISTENER_SERIALIZABLE_KEY = "listenerKey";

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        try{
            options = (SettingsDialogFragmentOptions) savedInstanceState.getSerializable(OPTIONS_SERIALIZABLE_KEY);
            listener = (SettingsDialogFragmentListener) savedInstanceState.getSerializable(LISTENER_SERIALIZABLE_KEY);
        }catch(NullPointerException ex){
            Log.e(DEFAULT_TAG, "Saved options in bottom dialog fragment is null");
        }
        return super.onCreateDialog(savedInstanceState);
    }

    public void setOptions(SettingsDialogFragmentOptions options){
        this.options = options;
    }

    public void setListener(SettingsDialogFragmentListener listener){ this.listener = listener;}

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.text_settings_bottom_sheet, null);
        dialog.setContentView(contentView);


        textSizeSeekBar = contentView.findViewById(R.id.textSizeSeekBar);
        currentSize = contentView.findViewById(R.id.currentSize);
        textSizeSeekBar.setOnSeekBarChangeListener(getChangeListener(currentSize));

        centeringSeekBar = contentView.findViewById(R.id.centeringSeekBar);

        shadowSizeSeekBar = contentView.findViewById(R.id.shadowSizeSeekBar);
        currentShadow = contentView.findViewById(R.id.currentShadow);
        shadowSizeSeekBar.setOnSeekBarChangeListener(getChangeListener(currentShadow));

        Button readyButton = contentView.findViewById(R.id.readyButton);
        readyButton.setOnClickListener(getReadyClickListener());

        configureAllSeekBars();

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    public SeekBar.OnSeekBarChangeListener getChangeListener(TextView textView){
        return new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                textView.setText(progress + " dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        };
    }

    public View.OnClickListener getReadyClickListener(){
        return v -> {

            int gravity = Gravity.LEFT;
            switch(centeringSeekBar.getProgress()){
                case 0:
                    gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                    break;
                case 1:
                    gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
                    break;
                case 2:
                    gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                    break;
            }
            listener.onOptionsChanged(new SettingsDialogFragmentOptions(
                    textSizeSeekBar.getProgress(),
                    gravity,
                    shadowSizeSeekBar.getProgress()));
            Log.d(TESTING_TAG, "getReadyClickListener() Gravity = " + gravity);
            dismiss();
        };
    }

    private void configureAllSeekBars(){
        int gravity = 0;
        Log.d(TESTING_TAG, "configureAllSeekBars() Gravity = " + options.getCurrentGravity());
        switch(options.getCurrentGravity()){
            case Gravity.LEFT | Gravity.CENTER_VERTICAL:
                gravity = 0;
                break;
            case Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL:
                gravity = 1;
                break;
            case Gravity.RIGHT | Gravity.CENTER_VERTICAL:
                gravity = 2;
                break;
        }
        centeringSeekBar.setProgress(gravity);
        textSizeSeekBar.setProgress(options.getCurrentSize());
        shadowSizeSeekBar.setProgress(options.getCurrentShadow());


    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(OPTIONS_SERIALIZABLE_KEY, options);
        outState.putSerializable(LISTENER_SERIALIZABLE_KEY, listener);
    }


    public interface SettingsDialogFragmentListener extends Serializable{

        void onOptionsChanged(SettingsDialogFragmentOptions options);
    }
}