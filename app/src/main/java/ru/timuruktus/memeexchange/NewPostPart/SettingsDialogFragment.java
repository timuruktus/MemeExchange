package ru.timuruktus.memeexchange.NewPostPart;


import android.app.Dialog;
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

import ru.timuruktus.memeexchange.R;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

public class SettingsDialogFragment extends BottomSheetDialogFragment{

    private SettingsDialogFragmentOptions options;
    public SettingsDialogFragmentListener listener;
    private SeekBar textSizeSeekBar;
    private SeekBar centeringSeekBar;
    private SeekBar shadowSizeSeekBar;
    private TextView currentSize;
    private TextView currentShadow;

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
            dismiss();
            int gravity = Gravity.LEFT;
            switch(centeringSeekBar.getProgress()){
                case 0:
                    gravity = Gravity.LEFT;
                    break;
                case 1:
                    gravity = Gravity.CENTER;
                    break;
                case 2:
                    gravity = Gravity.RIGHT;
                    break;
            }
            listener.onOptionsChanged(new SettingsDialogFragmentOptions(
                    textSizeSeekBar.getProgress(),
                    gravity,
                    shadowSizeSeekBar.getProgress()));
        };
    }

    private void configureAllSeekBars(){
        int gravity = 0;
        Log.d(TESTING_TAG, "Options = " + options);
        switch(options.currentGravity){
            case Gravity.LEFT:
                gravity = 0;
                break;
            case Gravity.CENTER:
                gravity = 1;
                break;
            case Gravity.RIGHT:
                gravity = 2;
                break;
        }
        centeringSeekBar.setProgress(gravity);
        textSizeSeekBar.setProgress(options.currentSize);
        shadowSizeSeekBar.setProgress(options.currentShadow);

    }



    public interface SettingsDialogFragmentListener{

        void onOptionsChanged(SettingsDialogFragmentOptions options);
    }
}