package ru.timuruktus.memeexchange.POJO.Queues;

import io.realm.RealmObject;
import ru.timuruktus.memeexchange.POJO.Meme;

public class NewMemeQueue extends RealmObject implements WebQueue{

    private boolean isImageSent;
    private boolean isMemeSent;
    private String imageUrl;
    private String imageUri;
    private String tags;
    private Meme meme;
    private String objectId;
    public NewMemeQueue(){
    }

    public NewMemeQueue(boolean isImageSent, boolean isMemeSent, String imageUrl, String imageUri, String tags, Meme meme, String objectId){
        this.isImageSent = isImageSent;
        this.isMemeSent = isMemeSent;
        this.imageUrl = imageUrl;
        this.imageUri = imageUri;
        this.tags = tags;
        this.meme = meme;
        this.objectId = objectId;
    }


    public boolean isImageSent(){
        return isImageSent;
    }

    public void setImageSent(boolean imageSent){
        isImageSent = imageSent;
    }

    public boolean isMemeSent(){
        return isMemeSent;
    }

    public void setMemeSent(boolean memeSent){
        isMemeSent = memeSent;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUri(){
        return imageUri;
    }

    public void setImageUri(String imageUri){
        this.imageUri = imageUri;
    }

    public String getTags(){
        return tags;
    }

    public void setTags(String tags){
        this.tags = tags;
    }

    public Meme getMeme(){
        return meme;
    }

    public void setMeme(Meme meme){
        this.meme = meme;
    }

    public String getObjectId(){
        return objectId;
    }

    public void setObjectId(String objectId){
        this.objectId = objectId;
    }

}
