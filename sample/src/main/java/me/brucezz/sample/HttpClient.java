package me.brucezz.sample;

import android.os.Environment;
import java.util.concurrent.TimeUnit;
import me.brucezz.apimock.MockInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brucezz on 2016-08-25.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public class HttpClient {
    private static final HttpClient INSTANCE = Inner.INSTANCE;

    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;

    private HttpClient() {

        mHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            // "/storage/emulated/0/MockAPI"
            .addInterceptor(new MockInterceptor(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MockAPI"))
            .build();

        mRetrofit = new Retrofit.Builder().baseUrl(APIService.BASE)
            .client(mHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static HttpClient getInstance() {
        return INSTANCE;
    }

    private static class Inner {
        private static final HttpClient INSTANCE = new HttpClient();
    }

    public <T> T create(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }
}

