package ru.timuruktus.memeexchange.NewPostPart;


import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import butterknife.ButterKnife;
import ru.timuruktus.memeexchange.R;

public class SettingsDialogFragment extends BottomSheetDialogFragment{

    private SettingsDialogFragmentOptions options;
    public SettingsDialogFragmentListener listener;
    private SeekBar textSizeSeekBar;
    private SeekBar centeringSeekBar;
    private SeekBar shadowSizeSeekBar;

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
        centeringSeekBar = contentView.findViewById(R.id.centeringSeekBar);
        shadowSizeSeekBar = contentView.findViewById(R.id.shadowSizeSeekBar);
        Button readyButton = contentView.findViewById(R.id.readyButton);
        readyButton.setOnClickListener(v -> listener.onOptionsChanged(new SettingsDialogFragmentOptions(
                textSizeSeekBar.getProgress(),
                centeringSeekBar.getProgress(),
                shadowSizeSeekBar.getProgress())));

        configureAllSeekBars();

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    private void configureAllSeekBars(){
        int gravity = 0;
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

    public class SettingsDialogFragmentOptions{
        protected int currentSize;
        protected int currentGravity;
        protected int currentShadow;

        public SettingsDialogFragmentOptions(int currentSize, int currentGravity, int currentShadow){
            this.currentSize = currentSize;
            this.currentGravity = currentGravity;
            this.currentShadow = currentShadow;
        }
    }

    public interface SettingsDialogFragmentListener{

        void onOptionsChanged(SettingsDialogFragmentOptions options);
    }
}