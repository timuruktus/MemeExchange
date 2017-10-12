package ru.timuruktus.memeexchange.Utils.Animation;

import android.animation.Animator;

public class ExecuteNextAfterEndListener implements Animator.AnimatorListener{

    private AnimationComposer animationComposer;
    private AnimationAction animationAction;

    public ExecuteNextAfterEndListener(AnimationComposer animationComposer, AnimationAction animationAction){
        this.animationComposer = animationComposer;
        this.animationAction = animationAction;
    }


    @Override
    public void onAnimationStart(Animator animation){

    }

    @Override
    public void onAnimationEnd(Animator animation){
        if(animationComposer != null){
            animationComposer.onAnimationEnded(animationAction);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation){

    }

    @Override
    public void onAnimationRepeat(Animator animation){

    }
}
