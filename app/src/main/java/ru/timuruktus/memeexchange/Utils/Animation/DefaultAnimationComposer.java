package ru.timuruktus.memeexchange.Utils.Animation;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import rx.Observable;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

public class DefaultAnimationComposer implements AnimationComposer{

    private ArrayList<AnimationAction> animationCollection = new ArrayList<>();
    private volatile boolean  isWorking = true;

    @Override
    public void addAnimation(AnimationAction animation){
        animationCollection.add(animation);
        animation.subscribe(this);
        animation.setDelay();
    }

    @Override
    public void addAnimations(ArrayList<AnimationAction> animations){
        animationCollection.addAll(animations);
        for(AnimationAction animation : animations){
            animation.subscribe(this);
            animation.setDelay();
        }
    }

    @Override
    public void insertAnimation(int position, AnimationAction animation){
        animationCollection.add(position, animation);
        animation.subscribe(this);
        animation.setDelay();
    }

    @Override
    public void replaceAnimation(AnimationAction oldAnimation, AnimationAction newAnimation){
        int oldIndex = animationCollection.indexOf(oldAnimation);
        animationCollection.remove(oldIndex);
        animationCollection.add(oldIndex, newAnimation);
        newAnimation.subscribe(this);
        newAnimation.setDelay();
    }

    @Override
    public void deleteAnimation(AnimationAction animation){
        animationCollection.remove(animation);
    }

    @Override
    public void deleteAllAnimations(){
        animationCollection.clear();
    }

    @Override
    public void start(){
        isWorking = true;
        if(isWorking){
            animationCollection.get(0).executeAction();
        }
    }

    @Override
    public void stop(){
        isWorking = false;
    }

    @Override
    public void onAnimationEnded(AnimationAction animation){
        int animationIndex = animationCollection.indexOf(animation);

        //was last anim
        if(animationIndex + 1 == animationCollection.size()){
            return;
        }
        long delay = animation.getDelay();
        new Handler().postDelayed(() -> animationCollection.get(animationIndex + 1).executeAction(), delay);

    }
}
