package me.brucezz.apimock;

/**
 * Created by brucezz on 2016-09-10.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public class MockConfigException extends Exception {
    public MockConfigException() {
    }

    public MockConfigException(String message) {
        super("Config Error: " + message);
    }

    public MockConfigException(String message, Throwable cause) {
        super("Config Error: " + message, cause);
    }
}
