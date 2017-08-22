package ru.timuruktus.memeexchange.POJO;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Meme extends RealmObject {

    @SerializedName("created")
    private long timestamp;
    private String image;
    private long likes;
    private String text;
    private boolean userLiked;
    @PrimaryKey
    private String objectId;
    private User author;

    public Meme() {

    }

    public Meme(long timestamp, String image, long likes, String text, boolean userLiked, String objectId, User author) {
        this.timestamp = timestamp;
        this.image = image;
        this.likes = likes;
        this.text = text;
        this.userLiked = userLiked;
        this.objectId = objectId;
        this.author = author;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUserLiked() {
        return userLiked;
    }

    public void setUserLiked(boolean userLiked) {
        this.userLiked = userLiked;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }



}
