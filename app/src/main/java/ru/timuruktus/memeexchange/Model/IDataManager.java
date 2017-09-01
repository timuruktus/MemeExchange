package ru.timuruktus.memeexchange.Model;

import java.util.List;

import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.User;
import rx.Observable;

public interface IDataManager {

    Observable<List<Meme>> loadMemesFromWeb(int pageSize, int offset);
    Observable<List<Meme>> loadMemesFromWeb(int pageSize, int offset, String sortBy);
    Observable<List<Meme>> loadMemesFromCache();
    Observable<List<Meme>> loadPopularGroups();
    Observable<List<Meme>> loadPopularMemesByMonth(int pageSize, int offset);
    Observable<List<Meme>> loadPopularMemesByWeek(int pageSize, int offset);
    Observable<List<Meme>> loadPopularMemesToday(int pageSize, int offset);
    String getAuthorNameByLogin(String login);
    Observable<User> loginUser(String login, String password);
    Observable<User> registerUser(String login, String password, String email);
}
