package ru.timuruktus.memeexchange.POJO;

import io.realm.RealmObject;
import ru.timuruktus.memeexchange.POJO.Queues.WebQueue;

public class LikeOperation extends RealmObject implements WebQueue{

    private String sentToId;
    private String receiverId;
    private long amount;


    public LikeOperation(String sentToId, String receiverId, long amount){
        this.sentToId = sentToId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public LikeOperation(){
    }

    public String getSentToId(){
        return sentToId;
    }

    public void setSentToId(String sentToId){
        this.sentToId = sentToId;
    }

    public String getReceiverId(){
        return receiverId;
    }

    public void setReceiverId(String receiverId){
        this.receiverId = receiverId;
    }

    public long getAmount(){
        return amount;
    }

    public void setAmount(long amount){
        this.amount = amount;
    }
}
