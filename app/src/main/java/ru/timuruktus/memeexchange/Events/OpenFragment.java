package ru.timuruktus.memeexchange.Events;


public class OpenFragment implements BusEvent{

    private String fragmentTag;

    public OpenFragment(String fragmentTag){
        this.fragmentTag = fragmentTag;
    }

    public String getFragmentTag(){
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag){
        this.fragmentTag = fragmentTag;
    }

}
