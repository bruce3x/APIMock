package me.brucezz.apimock;

import android.util.Log;
import java.io.File;
import java.io.FileReader;

import static me.brucezz.apimock.MockInterceptor.DEBUG;
import static me.brucezz.apimock.MockInterceptor.TAG;

/**
 * Created by brucezz on 2016-09-09.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public class Util {

    public static String readFile(File file) {
        try {
            FileReader reader = new FileReader(file);
            int len;
            char[] buffer = new char[1024];
            StringBuilder sb = new StringBuilder();
            while ((len = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, len);
            }
            return sb.toString();
        } catch (Exception e) {
            Util.warning("readFile file " + file.getAbsolutePath() + " failed", e);
            return null;
        }
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String removeQueryParams(String url) {
        if (isBlank(url)) return url;
        int index = url.indexOf("?");
        if (index == -1) return url;
        return url.substring(0, index);
    }

    public static void debug(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void warning(String message, Throwable tr) {
        if (DEBUG) {
            Log.w(TAG, message, tr);
        }
    }

    public static void warning(Throwable tr) {
        if (DEBUG) {
            Log.w(TAG, tr);
        }
    }

    public static void warning(String message) {
        if (DEBUG) {
            Log.w(TAG, message);
        }
    }
}
