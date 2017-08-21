package ru.timuruktus.memeexchange.POJO;

import io.realm.RealmObject;

public class User extends RealmObject {

    private String login;
    private String vkId;
    private String name;
    private String objectId;
    private String email;

    public User() {
    }

    public User(String login, String vkId, String name, String objectId, String email) {
        this.login = login;
        this.vkId = vkId;
        this.name = name;
        this.objectId = objectId;
        this.email = email;
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


}
