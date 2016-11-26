package me.brucezz.sample;

import android.support.annotation.NonNull;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.brucezz.apimock.MockInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

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
            .addInterceptor(new MockInterceptor())
            .build();

        mRetrofit = new Retrofit.Builder().baseUrl(GankAPI.BASE)
            .client(mHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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

    public Observable<GankData> firstAndroidData() {
        return create(GankAPI.class).getData(GankData.Category.ANDROID, 5, 1)
            .compose(handleResult())
            .flatMap(new Func1<List<GankData>, Observable<GankData>>() {
                @Override
                public Observable<GankData> call(List<GankData> dataList) {
                    return Observable.from(dataList);
                }
            })
            .take(1);
    }

    @NonNull
    private Observable.Transformer<GankAPI.Result<List<GankData>>, List<GankData>> handleResult() {
        return result -> result.flatMap(new Func1<GankAPI.Result<List<GankData>>, Observable<List<GankData>>>() {
            @Override
            public Observable<List<GankData>> call(GankAPI.Result<List<GankData>> result) {
                if (result.error) return Observable.error(new Exception());
                return Observable.just(result.data);
            }
        });
    }
}

