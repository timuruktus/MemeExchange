package ru.timuruktus.memeexchange.NewPostPart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;


public class MyOwnEditText extends android.support.v7.widget.AppCompatEditText{

    public MyOwnEditText(Context context){
        super(context);
    }

    public MyOwnEditText(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public MyOwnEditText(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }
}
