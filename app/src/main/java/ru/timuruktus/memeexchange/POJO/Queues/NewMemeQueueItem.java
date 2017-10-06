package ru.timuruktus.memeexchange.POJO.Queues;

import io.realm.RealmObject;
import ru.timuruktus.memeexchange.POJO.Meme;

public class NewMemeQueueItem extends RealmObject implements WebQueue{

    boolean isImageSended;
    boolean isMemeSended;
    String imageUrl;
    String imageUri;
    Meme meme;
}
