package ru.timuruktus.memeexchange.Model;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.Utils.NewestMemeComparator;
import rx.Observable;

import static ru.timuruktus.memeexchange.MainPart.MainActivity.DEFAULT_TAG;
import static ru.timuruktus.memeexchange.Utils.Settings.MEMES_CACHE_MAX_SIZE;

public class DatabaseHelper implements IDatabaseHelper{

    private static final String ID = "id";

    public static IDatabaseHelper getInstance(){
        return new DatabaseHelper();
    }

    @Override
    public void cacheMeme(final Meme meme) throws NullPointerException{
        Realm realm = Realm.getDefaultInstance();
        Log.d(DEFAULT_TAG, "Meme is cached");
        realm.executeTransaction(bgRealm -> {
            if(getMemesSize() >= MEMES_CACHE_MAX_SIZE){
                clearOldestMeme();
            }
            bgRealm.copyToRealmOrUpdate(meme);
        });
        realm.close();
    }




    @Override
    public void cacheMemes(List<Meme> memes){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm bgRealm){
                if(getMemesSize() + memes.size() >= MEMES_CACHE_MAX_SIZE){
                    clearOldestMemes(getMemesSize() + memes.size() - MEMES_CACHE_MAX_SIZE);
                }
                for(Meme meme : memes){
                    bgRealm.copyToRealmOrUpdate(meme);
                }
            }
        });
        realm.close();
    }

    @Override
    public void clearMeme(Meme meme){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                realm.where(Meme.class).equalTo("objectId", meme.getObjectId()).findFirst().deleteFromRealm();
            }
        });
        realm.close();
    }

    @Override
    public void clearMemes(List<Meme> memes){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                for(Meme meme : memes){
                    realm.where(Meme.class).equalTo("objectId", meme.getObjectId()).findFirst().deleteFromRealm();
                }
            }
        });
        realm.close();
    }

    @Override
    public void clearOldestMeme(){
        getMemes()
                .map(unsortedList -> {
                    Collections.sort(unsortedList, new NewestMemeComparator());
                    return unsortedList;
                })
                .subscribe(memeList -> clearMeme(memeList.get(memeList.size() - 1)));
    }

    @Override
    public void clearOldestMemes(int count){
        getMemes()
                .map(unsortedList -> {
                    Collections.sort(unsortedList, new NewestMemeComparator());
                    return unsortedList;
                })
                .subscribe(memeList -> {
                    for(int i = 0; i < count; i++){
                        clearMeme(memeList.get(memeList.size() - 1 - i));
                    }
                });
    }

    @Override
    public void updateMeme(Meme meme){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                realm.insertOrUpdate(meme);
            }
        });
        realm.close();
    }

    @Override
    public void updateMemes(List<Meme> memes){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                realm.insertOrUpdate(memes);
            }
        });
        realm.close();
    }

    @Override
    public int getMemesSize(){
        Realm realm = Realm.getDefaultInstance();
        List<Meme> memes = realm.copyFromRealm(realm.where(Meme.class).findAll());
        realm.close();
        return memes.size();
    }

    @Override
    public Observable<List<Meme>> getMemes(){
        Realm realm = Realm.getDefaultInstance();
        List<Meme> memes = realm.copyFromRealm(realm.where(Meme.class).findAll());
        realm.close();
        Log.d(DEFAULT_TAG, "Cache size is " + memes.size());
        return Observable.from(memes).toList();
    }

    @Override
    public Observable<Meme> getMemeById(String id){
        Realm realm = Realm.getDefaultInstance();
        Meme meme = null;
        Meme realmMeme = realm.where(Meme.class).equalTo("objectId", id).findFirst();
        if(realmMeme != null){
            meme = realm.copyFromRealm(realmMeme);
        }
        realm.close();
        return Observable.just(meme);
    }


}
