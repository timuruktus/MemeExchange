package ru.timuruktus.memeexchange.Utils;

import android.util.Log;

import java.util.Comparator;

import ru.timuruktus.memeexchange.POJO.Meme;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

public class NewestMemeComparator implements Comparator<Meme>{

    @Override
    public int compare(Meme o1, Meme o2){
        if(o1.getTimestamp() > o2.getTimestamp()){
            return -1;
        } else if(o1.getTimestamp() < o2.getTimestamp()){
            return 1;
        } else{
            return 0;
        }
    }
}
