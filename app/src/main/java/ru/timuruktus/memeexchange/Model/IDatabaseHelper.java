package ru.timuruktus.memeexchange.Model;

import java.util.List;

import io.realm.Realm;
import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observable;

public interface IDatabaseHelper {

    void cacheMeme(Meme shop);
    void cacheMemes(List<Meme> shops);
    Observable<List<Meme>> getMemes(Realm realm);
    Observable<Meme> getMemeById(Realm realm, String id);
}
