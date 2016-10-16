package me.brucezz.apimock;

import android.os.SystemClock;
import java.io.IOException;
import java.net.URL;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by brucezz on 2016-08-24.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public class MockInterceptor implements Interceptor {

    private String mRoot;
    private MockConfig mMockConfig;
    static boolean DEBUG = true;
    static String TAG = "APIMock";

    public MockInterceptor(String root) {
        this(root, true);
    }

    /**
     * @param debug 是否为 DEBUG 模式，默认开启
     */
    public MockInterceptor(String root, boolean debug) {
        mRoot = root;
        mMockConfig = MockConfig.parse(root);
        DEBUG = debug;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (mMockConfig == null) {
            mMockConfig = MockConfig.parse(mRoot);
        }

        if (mMockConfig == null) {
            return chain.proceed(request);
        }

        URL url = request.url().url();
        String mockData = mMockConfig.tryToIntercept(url.getPath());
        if (Util.isBlank(mockData)) {
            return chain.proceed(request);
        }

        SystemClock.sleep(mMockConfig.getMockDelay());
        return new Response.Builder().code(200)
            .message(mockData)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .addHeader("content-type", "application/json")
            .body(ResponseBody.create(MediaType.parse("application/json"), mockData.getBytes()))
            .build();
    }
}
