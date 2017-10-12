package ru.timuruktus.memeexchange.Utils.Animation;


import java.util.ArrayList;

public interface AnimationComposer{

    byte START_NEW_AFTER_END = 0;

    void addAnimation(AnimationAction animation);
    void addAnimations(ArrayList<AnimationAction> animations);
    void insertAnimation(int position, AnimationAction animation);
    void replaceAnimation(AnimationAction oldAnimation, AnimationAction newAnimation);
    void deleteAnimation(AnimationAction animation);
    void deleteAllAnimations();

    void start();
    void stop();

    void onAnimationEnded(AnimationAction animation);
}
