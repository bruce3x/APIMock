package me.brucezz.apimock;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/**
 * Created by brucezz on 2016-09-09.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public class MockConfig {

    private static final String FILE_NAME = "config.json";
    private static final String REMOTE = "remote";
    private static final String BASE_URL = "base";
    private static final String DELAY = "delay";
    private static final String ROUTE = "route";

    /**
     * 标识配置是否改变的空文件（修改配置会删掉原来所有的文件，重新 push 到设备上）
     */
    public static final String FLAG_FILE = ".notchanged";

    private static final int DEFAULT_MOCK_DELAY = 500;

    /**
     * 设备上配置文件存放位置
     */
    private String mRemote;
    /**
     * 拦截 API 的 Url 前缀
     */
    private String mBaseUrl;
    /**
     * 模拟延时
     */
    private int mMockDelay;
    /**
     * 需要拦截的路由表
     */
    private List<Route> mRouter;

    private MockConfig() {
        mRouter = new ArrayList<>();
    }

    /**
     * 解析配置
     *
     * @param root 配置文件在设备的路径
     */
    public static MockConfig parse(@NonNull String root) {
        if (TextUtils.isEmpty(root)) {
            throw new IllegalArgumentException("Argument 'root' passed to MockConfig is empty!");
        }

        MockConfig mockConfig = new MockConfig();
        if (mockConfig.load(root)) {
            return mockConfig;
        }
        return null;
    }

    public int getMockDelay() {
        return mMockDelay;
    }

    /**
     * 尝试拦截
     */
    public String tryToIntercept(String url) {

        if (!checkConfigNotChanged()) {
            // 配置发生改变
            mRouter.clear();
            load(mRemote);
        }

        for (Route route : mRouter) {
            if (route.hit(url)) {
                Util.debug("Mock hit!\n" +
                    "Url: " + (mBaseUrl + url) + "\n" +
                    "DataFile: " + route.getDataFilePath() + "\n");
                return route.getMockData();
            }
        }
        return null;
    }

    /**
     * 创建标识文件
     */
    private void createFlagFile() {
        try {
            boolean success = new File(mRemote, FLAG_FILE).createNewFile();
            if (success) return;
        } catch (IOException ignored) {
        }

        Util.warning("Create flag file failed .");
    }

    private boolean checkConfigNotChanged() {
        return new File(mRemote, FLAG_FILE).exists();
    }

    /**
     * 加载配置
     *
     * @return 加载配置成功，返回 true
     */
    private boolean load(@NonNull String root) {
        try {
            File configFile = new File(root, FILE_NAME);
            if (!configFile.exists()) {
                Util.warning(String.format("%s doesn't exist !", configFile.getAbsoluteFile()));
                return false;
            }

            JSONObject configObj = new JSONObject(Util.readFile(configFile));

            // 解析 remote 字段
            String remote = configObj.optString(REMOTE);
            if (!root.equals(remote)) {
                throw new MockConfigException("Remote path is not same between app and config file!");
            }

            // 解析 base_url 字段
            String baseUrl = configObj.optString(BASE_URL);
            if (Util.isBlank(baseUrl)) {
                throw new MockConfigException("Base url is blank!");
            }

            // 解析 delay 字段
            int delay = configObj.optInt(DELAY, DEFAULT_MOCK_DELAY);

            // 解析路由配置
            JSONObject routes = configObj.optJSONObject(ROUTE);
            if (routes == null) {
                throw new MockConfigException("Parse routes failed!");
            }

            // 遍历 key
            List<Route> router = new ArrayList<>();
            Iterator<String> iterator = routes.keys();
            while (iterator.hasNext()) {
                String rule = iterator.next();
                String dataPath = routes.optString(rule);

                try {
                    router.add(new Route(rule, root + File.separator + dataPath));
                } catch (IllegalArgumentException e) {
                    Util.warning(e);
                }
            }

            mRemote = remote;
            mBaseUrl = baseUrl;
            mMockDelay = delay;
            mRouter = router;
            if (!checkConfigNotChanged()) {
                createFlagFile();
            }

            Util.debug("Load config.json successful from " + root);
            return true;
        } catch (Exception e) {
            Util.warning("Load config.json failed, config won't change.", e);
            return false;
        }
    }
}
