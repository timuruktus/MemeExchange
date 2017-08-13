package ru.timuruktus.memeexchange.Model;

import java.util.List;

import io.realm.Realm;
import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observable;

public interface IDataManager {

    Observable<List<Meme>> loadMemesFromWeb();
    Observable<List<Meme>> loadMemesFromCache(Realm realm);
}
