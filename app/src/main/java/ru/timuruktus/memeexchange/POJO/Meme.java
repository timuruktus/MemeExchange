package ru.timuruktus.memeexchange.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Meme extends RealmObject implements RecyclerItem {

    @SerializedName("created")
    private long timestamp;
    private String image;
    private long likes;
    private String text;
    private boolean userLiked;
    @PrimaryKey
    private String objectId;
    private User author;
    private RealmList<RealmString> tags;

    public Meme() {

    }

    public Meme(long timestamp, String image, long likes, String text, boolean userLiked, String objectId, User author, RealmList<RealmString> tags){
        this.timestamp = timestamp;
        this.image = image;
        this.likes = likes;
        this.text = text;
        this.userLiked = userLiked;
        this.objectId = objectId;
        this.author = author;
        this.tags = tags;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public Meme setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Meme setImage(String image) {
        this.image = image;
        return this;
    }

    public long getLikes() {
        return likes;
    }

    public Meme setLikes(long likes) {
        this.likes = likes;
        return this;
    }

    public String getText() {
        return text;
    }

    public Meme setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isUserLiked() {
        return userLiked;
    }

    public Meme setUserLiked(boolean userLiked) {
        this.userLiked = userLiked;
        return this;
    }

    public String getObjectId() {
        return objectId;
    }

    public Meme setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Meme setAuthor(User author) {
        this.author = author;
        return this;
    }

    public RealmList<RealmString> getTags(){
        return tags;
    }

    public Meme setTags(RealmList<RealmString> tags){
        this.tags = tags;
        return this;
    }




}
