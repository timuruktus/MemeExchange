package ru.timuruktus.memeexchange.Utils;

import android.media.AudioManager;
import android.media.SoundPool;

public class SoundUtils{


    public static final int MAX_MUSIC_STREAMS = 5;
    public static SoundPool soundPool;
    public static final int HIGH_PRIORITY_SOUND = 2;
    public static final int MEDIUM_PRIORITY_SOUND = 1;
    public static final int LOW_PRIORITY_SOUND = 0;



    public static SoundPool getSoundPool(){
        if(soundPool == null){
            createSoundPool();
            return soundPool;
        }
        return soundPool;
    }

    private static void createSoundPool(){
        soundPool = new SoundPool(MAX_MUSIC_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }

}
