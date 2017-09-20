package ru.timuruktus.memeexchange;

import org.junit.Test;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.timuruktus.memeexchange.Model.DataManager;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.RESTBodies.POSTLogin;
import ru.timuruktus.memeexchange.POJO.User;
import ru.timuruktus.memeexchange.REST.BackendlessAPI;
import rx.Observer;
import rx.functions.Func1;

import static org.junit.Assert.*;
import static ru.timuruktus.memeexchange.REST.BackendlessAPI.BASE_URL;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testApiCurrentTime() throws Exception {
        Retrofit backendlessRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        System.out.println(backendlessAPI.currentServerTime().execute().body());

    }

    @Test
    public void testApiGetUsernameByLogin() throws Exception {
        Retrofit backendlessRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        // OLD! Now retrieve Observable
//        System.out.println(backendlessAPI.getUserByCondition("login='timuruktus'", null).execute().body().get(0).getEmail());
//        backendlessAPI.getUserByCondition("timuruktus").enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                System.out.println(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });

    }

    @Test
    public void testApiGetMemes() throws Exception{
        Retrofit backendlessRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        backendlessAPI.listNewestMemes(0, 0, null).map(new Func1<List<Meme>, List<Meme>>(){
            @Override
            public List<Meme> call(List<Meme> memes){
                for(Meme meme : memes){
                    System.out.println("Text: " + meme.getText());
                    System.out.println("Author: " + meme.getAuthor());
                    System.out.println("Likes: " + meme.getLikes());
                    System.out.println("Image: " + meme.getImage());
                }
                return null;
            }
        }).subscribe();
    }

    @Test
    public void testApiMemesAuthor(){
        Retrofit backendlessRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        backendlessAPI.listNewestMemes(0, 0, null).subscribe(memes -> System.out.println(memes.get(0).getAuthor().getEmail()));
    }

    // We should save objectId and user-token both
    @Test
    public void testLoginApi(){
        Retrofit backendlessRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BackendlessAPI backendlessAPI = backendlessRetrofit.create(BackendlessAPI.class);
        POSTLogin body = new POSTLogin("timuruktus", "qwerty");
        System.out.println(backendlessAPI.loginUser(body).subscribe(new Observer<User>(){
            @Override
            public void onCompleted(){

            }

            @Override
            public void onError(Throwable e){
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(User user){
                System.out.println(user.getToken());
            }
        }));
    }

    @Test
    public void testLoginDataManagerApi(){
        DataManager.getInstance().loginUser("s", "qwerty").subscribe(new Observer<User>(){
            @Override
            public void onCompleted(){

            }

            @Override
            public void onError(Throwable e){
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(User user){
                System.out.println(user.getToken());
            }
        });

    }


    @Test
    public void testRegisterApi(){
        DataManager dataManager = DataManager.getInstance();
        dataManager.registerUser("timuruktus", "qwerty", "keks@keks.keks").subscribe(new Observer<User>(){
            @Override
            public void onCompleted(){
                System.out.println("OnComplete");
            }

            @Override
            public void onError(Throwable e){
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(User user){
                System.out.println("Registered!");
            }
        });
    }
}