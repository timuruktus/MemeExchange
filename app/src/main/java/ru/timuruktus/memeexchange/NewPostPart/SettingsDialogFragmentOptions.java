package ru.timuruktus.memeexchange.NewPostPart;

import java.io.Serializable;

public class SettingsDialogFragmentOptions implements Serializable{
    public int getCurrentSize(){
        return currentSize;
    }

    public void setCurrentSize(int currentSize){
        this.currentSize = currentSize;
    }

    public int getCurrentGravity(){
        return currentGravity;
    }

    public void setCurrentGravity(int currentGravity){
        this.currentGravity = currentGravity;
    }

    public int getCurrentShadow(){
        return currentShadow;
    }

    public void setCurrentShadow(int currentShadow){
        this.currentShadow = currentShadow;
    }

    private int currentSize;
    private int currentGravity;
    private int currentShadow;


    public SettingsDialogFragmentOptions(int currentSize, int currentGravity, int currentShadow){
        this.currentSize = currentSize;
        this.currentGravity = currentGravity;
        this.currentShadow = currentShadow;
    }
}
