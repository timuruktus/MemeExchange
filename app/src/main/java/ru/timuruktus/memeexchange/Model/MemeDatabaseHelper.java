package ru.timuruktus.memeexchange.Model;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.Queues.NewMemeQueue;
import ru.timuruktus.memeexchange.POJO.Queues.SavedMemeQueue;
import ru.timuruktus.memeexchange.Utils.NewestMemeComparator;
import rx.Observable;


public class MemeDatabaseHelper implements IMemeDatabaseHelper{

    private static final String ID = "id";
    public static final int MEMES_CACHE_MAX_SIZE = 50; //Memes
    public static final int SAVED_MEMES_MAX_SIZE = 5; //Memes

    public static IMemeDatabaseHelper getInstance(){
        return new MemeDatabaseHelper();
    }

    @Override
    public void cacheMeme(final Meme meme) throws NullPointerException{
        Realm realm = Realm.getDefaultInstance();
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

    @Override
    public void updateNewMemeQueue(NewMemeQueue queue){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(queue));
        realm.close();
    }

    @Override
    public void removeNewMemeQueue(NewMemeQueue queue){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                realm.where(NewMemeQueue.class).equalTo("objectId", queue.getObjectId()).findFirst().deleteFromRealm();
            }
        });
        realm.close();
    }

    @Override
    public Observable<List<NewMemeQueue>> getAllNewMemeQueues(){
        Realm realm = Realm.getDefaultInstance();
        List<NewMemeQueue> queues = realm.copyFromRealm(realm.where(NewMemeQueue.class).findAll());
        realm.close();
        return Observable.from(queues).toList();
    }

    @Override
    public void updateSavedMemeQueue(SavedMemeQueue queue){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(bgRealm -> {
            if(getMemesSize() >= SAVED_MEMES_MAX_SIZE){
                clearOldestSavedMeme();
            }
            bgRealm.copyToRealmOrUpdate(queue);
        });
        realm.close();

    }

    private void clearOldestSavedMeme(){
        getAllSavedMemeQueues()
                .subscribe(memeList -> removeSavedMemeQueue(memeList.get(memeList.size() - 1)));
    }

    @Override
    public void removeSavedMemeQueue(SavedMemeQueue queue){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                realm.where(SavedMemeQueue.class).equalTo("id", queue.getId()).findFirst().deleteFromRealm();
            }
        });
        realm.close();
    }

    @Override
    public Observable<List<SavedMemeQueue>> getAllSavedMemeQueues(){
        Realm realm = Realm.getDefaultInstance();
        List<SavedMemeQueue> queues = realm.copyFromRealm(realm.where(SavedMemeQueue.class).findAll());
        realm.close();
        return Observable.from(queues).toList();
    }


}
