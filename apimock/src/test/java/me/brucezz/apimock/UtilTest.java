package me.brucezz.apimock;

import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by brucezz on 2016-09-09.
 * Github: https://github.com/brucezz
 * Email: im.brucezz@gmail.com
 */
public class UtilTest {

    @org.junit.Rule public TemporaryFolder mTemporaryFolder = new TemporaryFolder();

    @Test
    public void testIsBlank() {
        assertTrue(Util.isBlank(null));
        assertTrue(Util.isBlank(""));
        assertTrue(Util.isBlank("  "));
        assertTrue(Util.isBlank("\n"));
        assertFalse(Util.isBlank("123"));
        assertFalse(Util.isBlank("123 "));
        assertFalse(Util.isBlank(" 123 "));
    }

    @Test
    public void testRemoveQueryParams() {
        assertEquals(Util.removeQueryParams(""), "");
        assertEquals(Util.removeQueryParams(null), null);
        assertEquals(Util.removeQueryParams("http://brucezz.leanapp.cn/hello"), "http://brucezz.leanapp.cn/hello");
        assertEquals(Util.removeQueryParams("http://brucezz.leanapp.cn/hello?test=true"), "http://brucezz.leanapp.cn/hello");
        assertEquals(Util.removeQueryParams("http://brucezz.leanapp.cn/hello?test1=true&test2=false"),
            "http://brucezz.leanapp.cn/hello");
    }
}
