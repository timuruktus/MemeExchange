package ru.timuruktus.memeexchange.Utils.Animation;

public abstract class AnimationAction{

    public AnimationComposer subject;
    public long delay;

    public abstract void executeAction() throws NullPointerException;
    public abstract void setDelay();
    public long getDelay(){
        return delay;
    }

    void subscribe(AnimationComposer subject){
        this.subject = subject;
    }
}
