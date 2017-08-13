package ru.timuruktus.memeexchange.Model;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observable;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;
import static ru.timuruktus.memeexchange.MainPart.MainActivity.TESTING_TAG;

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
    public Observable<List<Meme>> getMemes(Realm realm) {

        Log.d(TESTING_TAG, "We are in getMemes() in DatabaseHelper ");
        RealmResults<Meme> results = realm.where(Meme.class)
                .findAll();
        Log.d(DEFAULT_TAG, "Cache size is " + results.size());
        return Observable.from(results).toList();
    }

    @Override
    public Observable<Meme> getMemeById(Realm realm, String id) {
        RealmResults<Meme> results = realm.where(Meme.class).contains(ID, id).findAll();
        return Observable.from(results);
    }


}
