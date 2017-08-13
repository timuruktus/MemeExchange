package ru.timuruktus.memeexchange.REST;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import ru.timuruktus.memeexchange.POJO.Meme;
import rx.Observable;

public interface BackendlessAPI {

    //    https://api.backendless.com/<application-id>/<api-key>/<operation-specific-path>
    String APP_ID = "7DDF8A37-3FEA-7574-FFFD-F85AA25C6C00";
    String API_KEY = "70EBDF10-0B9F-F91F-FF59-908EB9543700";
    String BASE_URL = "https://api.backendless.com/" + APP_ID + "/" + API_KEY +"/";
    String IMAGES_FOLDER = "memes";
    String IMAGES_URL = BASE_URL + "files/" + IMAGES_FOLDER + "/";

    @GET("data/Meme")
    Observable<List<Meme>> listMemes();

    @Multipart
    @Headers({"Content-Type: multipart/form-data"})
    @POST("files/memes/{fileName}")
    Call<ResponseBody> upload(
//            @Part("description") RequestBody description,
            @Part MultipartBody.Part file,
            @Path("fileName") String fileName
    );


}
