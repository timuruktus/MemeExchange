package ru.timuruktus.memeexchange.Model;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observable;

public interface IDatabaseHelper {

    void cacheMeme(Meme meme);
    void cacheMemes(List<Meme> memes);
    void updateMeme(Meme meme);
    void updateMemes(List<Meme> memes);
    Observable<List<Meme>> getMemes();
    Observable<Meme> getMemeById(String id);

}
