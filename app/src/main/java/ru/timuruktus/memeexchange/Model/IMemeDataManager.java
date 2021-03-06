package ru.timuruktus.memeexchange.Model;

import android.util.Pair;

import java.util.List;

import ru.timuruktus.memeexchange.Errors.NullTokenException;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.Queues.SavedMemeQueue;
import ru.timuruktus.memeexchange.POJO.RecyclerItem;
import ru.timuruktus.memeexchange.POJO.User;
import rx.Observable;

public interface IMemeDataManager extends IDataManager{

    Observable<List<Meme>> loadMemesFromWeb(int pageSize, int offset);
    Observable<List<Meme>> loadMemesFromWeb(int pageSize, int offset, String sortBy);
    Observable<List<Meme>> loadMemesFromCache();
    Observable<List<Meme>> loadPopularGroups();
    Observable<List<Meme>> loadPopularMemesByMonth(int pageSize, int offset);
    Observable<List<Meme>> loadPopularMemesByWeek(int pageSize, int offset);
    Observable<List<Meme>> loadPopularMemesToday(int pageSize, int offset);
    Observable<List<RecyclerItem>> loadUserPosts(String userId, int pageSize, int offset);

    Observable<List<Meme>> loadSavedNonCreatedMemes();
    void saveNonCreatedMeme(SavedMemeQueue meme);
}
