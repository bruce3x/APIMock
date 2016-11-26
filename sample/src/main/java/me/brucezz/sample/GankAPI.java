package me.brucezz.sample;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by brucezz on 2016-08-25.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public interface GankAPI {
    /**
     * See http://gank.io/api
     */
    String BASE = "http://gank.io/api/";

    @GET("data/{category}/{size}/{page}")
    Observable<Result<List<GankData>>> getData(@GankData.Category @Path("category") String category, @Path("size") int size,
        @Path("page") int page);

    class Result<T> {
        @SerializedName("error") public boolean error;
        @SerializedName("results") public T data;
    }
}
