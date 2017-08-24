package ru.timuruktus.memeexchange.Model;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observable;

public interface IDataManager {

    Observable<List<Meme>> loadMemesFromWeb();
    Observable<List<Meme>> loadMemesFromCache();
    Observable<List<Meme>> loadPopularGroups();
    Observable<List<Meme>> loadPopularMemesByMonth();
    Observable<List<Meme>> loadPopularMemesByWeek();
    Observable<List<Meme>> loadPopularMemesToday();
    String getAuthorNameByLogin(String login);
}
