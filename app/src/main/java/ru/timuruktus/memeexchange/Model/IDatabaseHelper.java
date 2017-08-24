package ru.timuruktus.memeexchange.Model;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observable;

public interface IDatabaseHelper {

    void cacheMeme(Meme shop);
    void cacheMemes(List<Meme> shops);
    Observable<List<Meme>> getMemes();
    Observable<Meme> getMemeById(String id);
}
