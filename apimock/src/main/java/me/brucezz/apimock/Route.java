package me.brucezz.apimock;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by brucezz on 2016-10-16.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */

public class Route {

    /**
     * Mock 规则
     */
    private Pattern mRule;

    /**
     * Mock 数据文件
     */
    private File mDataFile;

    /**
     * Mock 数据内容
     */
    private String mDataContent;

    public Route(String rule, String filePath) throws IllegalArgumentException {
        if (!Util.isBlank(rule) && !Util.isBlank(filePath)) {
            this.mDataFile = new File(filePath);
            this.mRule = Pattern.compile(rule);
            if (mRule != null && mDataFile.exists()) return;
        }

        throw new IllegalArgumentException(String.format("Route: \"%s\" -> %s is invalid.", rule, filePath));
    }

    /**
     * 获取 Mock 数据
     */
    public String getMockData() {
        if (mDataContent == null) {
            mDataContent = Util.readFile(mDataFile);
        }
        return mDataContent;
    }

    /**
     * 是否命中此条规则
     */
    public boolean hit(String url) {
        return mRule.matcher(url).matches();
    }

    public String getDataFilePath() {
        return mDataFile.getAbsolutePath();
    }
}
