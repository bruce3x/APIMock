package me.brucezz.sample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by brucezz on 2016-08-25.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public interface APIService {

    String BASE = "http://brucezz.leanapp.cn";

    @GET("{name}")
    Call<Result> hello(@Path("name") String name);
}
