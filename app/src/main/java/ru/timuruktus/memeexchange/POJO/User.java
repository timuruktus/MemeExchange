package ru.timuruktus.memeexchange.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject implements RecyclerItem {

    private String login;
    private String vkId;
    private String name;
    private long likes;
    private long subscribers;
    private RealmList<LikeOperation> lastOperations;
    private String objectId;
    private String ownerId;
    private String email;
    private String avatar; // Image URL
    private boolean isUserSubscribed;

    @SerializedName("user-token")
    private String token;

    public User() {
    }

    public User(String login, String vkId, String name, long likes, long subscribers, RealmList<LikeOperation> lastOperations, String objectId, String ownerId, String email, String avatar, boolean isUserSubscribed, String token){
        this.login = login;
        this.vkId = vkId;
        this.name = name;
        this.likes = likes;
        this.subscribers = subscribers;
        this.lastOperations = lastOperations;
        this.objectId = objectId;
        this.ownerId = ownerId;
        this.email = email;
        this.avatar = avatar;
        this.isUserSubscribed = isUserSubscribed;
        this.token = token;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public long getLikes(){
        return likes;
    }

    public void setLikes(long likes){
        this.likes = likes;
    }

    public RealmList<LikeOperation> getLastOperations(){
        return lastOperations;
    }

    public void setLastOperations(RealmList<LikeOperation> lastOperations){
        this.lastOperations = lastOperations;
    }

    public long getSubscribers(){
        return subscribers;
    }

    public void setSubscribers(long subscribers){
        this.subscribers = subscribers;
    }

    public boolean isUserSubscribed(){
        return isUserSubscribed;
    }

    public void setUserSubscribed(boolean userSubscribed){
        isUserSubscribed = userSubscribed;
    }

    public String getOwnerId(){
        return ownerId;
    }

    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }







}
