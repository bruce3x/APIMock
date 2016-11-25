package me.brucezz.sample;

/**
 * Created by brucezz on 2016-08-25.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public class Result {
    /**
     * code : 200
     * message : Hello, hello~
     */

    public int code;
    public String message;

    @Override
    public String toString() {
        return "Result{" +
            "code=" + code +
            ", message='" + message + '\'' +
            '}';
    }
}
