package ru.timuruktus.memeexchange.Model;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.Queues.NewMemeQueue;
import ru.timuruktus.memeexchange.POJO.Queues.SavedMemeQueue;
import rx.Observable;

public interface IMemeDatabaseHelper{

    void cacheMeme(Meme meme);
    void cacheMemes(List<Meme> memes);
    void clearMeme(Meme meme);
    void clearMemes(List<Meme> meme);
    void clearOldestMeme();
    void clearOldestMemes(int count);
    void updateMeme(Meme meme);
    void updateMemes(List<Meme> memes);
    int getMemesSize();
    Observable<List<Meme>> getMemes();
    Observable<Meme> getMemeById(String id);

    void updateNewMemeQueue(NewMemeQueue queue);
    void removeNewMemeQueue(NewMemeQueue queue);
    Observable<List<NewMemeQueue>> getAllNewMemeQueues();

    void updateSavedMemeQueue(SavedMemeQueue queue);
    void removeSavedMemeQueue(SavedMemeQueue queue);
    Observable<List<SavedMemeQueue>> getAllSavedMemeQueues();



}
