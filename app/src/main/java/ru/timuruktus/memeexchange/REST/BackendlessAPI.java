package ru.timuruktus.memeexchange.REST;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.timuruktus.memeexchange.POJO.Meme;
import ru.timuruktus.memeexchange.POJO.POSTLogin;
import ru.timuruktus.memeexchange.POJO.POSTRegister;
import ru.timuruktus.memeexchange.POJO.User;
import rx.Observable;

public interface BackendlessAPI {

    //    https://api.backendless.com/<application-id>/<api-key>/<operation-specific-path>
    String APP_ID = "7DDF8A37-3FEA-7574-FFFD-F85AA25C6C00";
    String API_KEY = "70EBDF10-0B9F-F91F-FF59-908EB9543700";
    String BASE_URL = "https://api.backendless.com/" + APP_ID + "/" + API_KEY +"/";
    String IMAGES_FOLDER = "memes";
    String IMAGES_URL = BASE_URL + "files/" + IMAGES_FOLDER + "/";

    @GET("data/Meme")
    Observable<List<Meme>> listNewestMemes(@Query("pageSize") int pageSize, @Query("offset") int offset, @Query("sortBy") String sortBy);

    @GET("data/Meme")
    Observable<List<Meme>> listMemesFromGroup(@Query("pageSize") int pageSize,
                                              @Query("offset") int offset
                                              //@Query("groupId") int or maybe String related to group ID
    );

    @Multipart
    @Headers({"Content-Type: multipart/form-data"})
    @POST("files/memes/{fileName}")
    Call<ResponseBody> upload(
//            @Part("description") RequestBody description,
            @Part MultipartBody.Part file,
            @Path("fileName") String fileName
    );

    @GET("services/api/currenttime")
    Call<Long> currentServerTime();

    @GET("data/Users")
    Observable<List<User>> getUserByCondition(@Query("where") String condition, @Header("user-token") String userToken);

    @POST("users/login")
    @Headers({"Content-Type:application/json"})
    Observable<User> loginUser(@Body POSTLogin body);

    @POST("users/register")
    @Headers({"Content-Type:application/json"})
    Observable<User> registerUser(@Body POSTRegister body);


}
