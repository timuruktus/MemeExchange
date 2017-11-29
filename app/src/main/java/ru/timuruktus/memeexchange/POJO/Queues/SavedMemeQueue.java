package ru.timuruktus.memeexchange.POJO.Queues;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.timuruktus.memeexchange.POJO.Meme;

public class SavedMemeQueue extends RealmObject implements Queue{

    private Meme meme;
    @PrimaryKey
    private int id;

    public SavedMemeQueue(Meme meme, int id){
        this.meme = meme;
        this.id = id;
    }


    public Meme getMeme(){
        return meme;
    }

    public void setMeme(Meme meme){
        this.meme = meme;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }




}
