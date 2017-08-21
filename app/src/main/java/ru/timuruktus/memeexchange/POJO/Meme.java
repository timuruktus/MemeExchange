package ru.timuruktus.memeexchange.POJO;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Meme extends RealmObject {

    @SerializedName("created")
    private long timestamp;
    private String url;
    private long likesCount;
    private String text;
    private boolean userLiked;
    private String objectId;
    private User author;

    public Meme() {

    }

    public Meme(long timestamp, String url, long likesCount, String text, boolean userLiked, String objectId, User author) {
        this.timestamp = timestamp;
        this.url = url;
        this.likesCount = likesCount;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
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
