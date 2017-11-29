package ru.timuruktus.memeexchange.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;


public class WindowMethods{

    public static final byte MESSAGE_ON_TOP = 0;
    public static final byte MESSAGE_ON_BOTTOM = 1;


    public static void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }


    public void setSnackbarLocation(byte location, Snackbar snackbar){
        int gravity = Gravity.BOTTOM;
        if(location == MESSAGE_ON_TOP){
            gravity = Gravity.TOP;
        }
        final ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
        if(params instanceof CoordinatorLayout.LayoutParams){
            ((CoordinatorLayout.LayoutParams) params).gravity = gravity;
        }else{
            ((FrameLayout.LayoutParams) params).gravity = gravity;
        }
        snackbar.getView().setLayoutParams(params);
    }
}
