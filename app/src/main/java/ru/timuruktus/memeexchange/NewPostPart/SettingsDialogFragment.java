package ru.timuruktus.memeexchange.NewPostPart;


import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;

import ru.timuruktus.memeexchange.R;

public class SettingsDialogFragment extends BottomSheetDialogFragment{

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

    private SettingsDialogFragmentOptions options;


    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.text_settings_bottom_sheet, null);
        dialog.setContentView(contentView);


        SeekBar textSizeSeekBar = contentView.findViewById(R.id.textSizeSeekBar);
        SeekBar centeringSeekBar = contentView.findViewById(R.id.centeringSeekBar);
        SeekBar shadowSizeSeekBar = contentView.findViewById(R.id.shadowSizeSeekBar);
        configureAllSeekBars();

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    private void configureAllSeekBars(){
        int gravity;
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
        //TODO

    }

    public class SettingsDialogFragmentOptions{
        protected float currentSize;
        protected int currentGravity;
        protected float currentShadow;

        public SettingsDialogFragmentOptions(float currentSize, int currentGravity, float currentShadow){
            this.currentSize = currentSize;
            this.currentGravity = currentGravity;
            this.currentShadow = currentShadow;
        }
    }
}