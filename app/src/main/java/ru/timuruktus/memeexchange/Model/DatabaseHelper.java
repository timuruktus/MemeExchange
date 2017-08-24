package ru.timuruktus.memeexchange.Model;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observable;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;

public class DatabaseHelper implements IDatabaseHelper {

    private static final String ID = "id";

    public static IDatabaseHelper getInstance() {
        return new DatabaseHelper();
    }

    @Override
    public void cacheMeme(final Meme meme) throws NullPointerException {
        Realm realm = Realm.getDefaultInstance();
        Log.d(DEFAULT_TAG, "Meme is cached");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealmOrUpdate(meme);
            }
        });
        realm.close();
    }


    @Override
    public void cacheMemes(List<Meme> memes) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                for(Meme meme : memes) {
                    bgRealm.copyToRealmOrUpdate(meme);
                }
            }
        });
        realm.close();
    }

    @Override
    public Observable<List<Meme>> getMemes() {
        Realm realm = Realm.getDefaultInstance();
        List<Meme> memes = realm.copyFromRealm(realm.where(Meme.class).findAll());
        realm.close();
        Log.d(DEFAULT_TAG, "Cache size is " + memes.size());
        return Observable.from(memes).toList();
    }
    @Override public Observable<Meme> getMemeById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Meme meme = null;
        Meme realmMeme = realm.where(Meme.class).equalTo("objectId", id).findFirst();
        if (realmMeme != null) {
            meme = realm.copyFromRealm(realmMeme);
        }
        realm.close();
        return Observable.just(meme);
    }


}
