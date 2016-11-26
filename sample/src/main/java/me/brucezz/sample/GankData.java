package me.brucezz.sample;

import android.support.annotation.StringDef;
import com.google.gson.annotations.SerializedName;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by brucezz on 2016-11-26.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */

public class GankData {
    /**
     * _id : 5836a7fc421aa91cb7afe7e0
     * createdAt : 2016-11-24T16:42:36.919Z
     * desc : 支持https的ijkplayer播放器
     * images : ["http://img.gank.io/22aa7a50-de1f-4697-8eb8-7bcc247cce58"]
     * publishedAt : 2016-11-25T11:29:49.832Z
     * source : web
     * type : Android
     * url : https://github.com/l123456789jy/ijkplayer
     * used : true
     * who : Lazy
     */

    @SerializedName("_id") public String id;
    @SerializedName("createdAt") public String createdAt;
    @SerializedName("desc") public String desc;
    @SerializedName("publishedAt") public String publishedAt;
    @SerializedName("source") public String source;
    @SerializedName("type") public String type;
    @SerializedName("url") public String url;
    @SerializedName("used") public boolean used;
    @SerializedName("who") public String who;
    @SerializedName("images") public List<String> images;

    @StringDef({
        Category.ANDROID, Category.IOS, Category.GIRLS, Category.VIDEOS, Category.FRONTEND, Category.RESOURCES, Category.ALL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Category {
        //福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
        String ANDROID = "Android";
        String IOS = "iOS";
        String GIRLS = "福利";
        String VIDEOS = "休息视频";
        String FRONTEND = "前端";
        String RESOURCES = "拓展资源";
        String ALL = "all";
    }
}
