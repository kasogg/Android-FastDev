/**
 * @系统项目名称:Openfire项目研究
 * @author leo
 * @version 1.0
 * @创建时间：2014-2-27 下午11:51:31
 * @copyright:ilindo公司-版权所有
 * @描述:
 */
package cn.kasogg.common.util;

import android.util.Log;

import static cn.kasogg.common.util.constants.Constants.DEBUG;

public class LogUtils {
    private static String DEFAULT_TAG = "LOG";

    public static void debug(Object o) {
        debug(DEFAULT_TAG, o);
    }

    public static void info(Object o) {
        info(DEFAULT_TAG, o);
    }

    public static void warn(Object o) {
        warn(DEFAULT_TAG, o);
    }

    public static void error(Object o) {
        error(DEFAULT_TAG, o);
    }

    public static void debug(String tag, Object o) {
        if (DEBUG)
            Log.d(tag, o == null ? "" : o.toString());
    }

    public static void info(String tag, Object o) {
        if (DEBUG)
            Log.i(tag, o == null ? "" : o.toString());
    }

    public static void warn(String tag, Object o) {
        if (DEBUG)
            Log.w(tag, o == null ? "" : o.toString());
    }

    public static void error(String tag, Object o) {
        if (DEBUG)
            Log.e(tag, o == null ? "" : o.toString());
    }

    public static void debug(Class<?> c, Object o) {
        if (DEBUG)
            Log.d(c.getSimpleName(), o == null ? "" : o.toString());
    }

    public static void info(Class<?> c, Object o) {
        if (DEBUG)
            Log.i(c.getSimpleName(), o == null ? "" : o.toString());
    }

    public static void warn(Class<?> c, Object o) {
        if (DEBUG)
            Log.w(c.getSimpleName(), o == null ? "" : o.toString());
    }

    public static void error(Class<?> c, Object o) {
        if (DEBUG)
            Log.e(c.getSimpleName(), o == null ? "" : o.toString());
    }
}
