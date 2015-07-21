/**
 * @系统项目名称:Openfire项目研究
 * @author leo
 * @version 1.0
 * @创建时间：2014-2-27 下午11:51:31
 * @copyright:ilindo公司-版权所有
 * @描述:
 */
package cn.kasogg.booster.util;

import android.util.Log;

public class LogUtils {
    public static final boolean IS_LOG = true;
    private static final String DEFAULT_TAG = "LOG";

    private LogUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static void d(Object o) {
        d(DEFAULT_TAG, o);
    }

    public static void i(Object o) {
        i(DEFAULT_TAG, o);
    }

    public static void w(Object o) {
        w(DEFAULT_TAG, o);
    }

    public static void e(Object o) {
        e(DEFAULT_TAG, o);
    }

    public static void d(String tag, Object o) {
        if (IS_LOG)
            Log.d(tag, o == null ? "" : o.toString());
    }

    public static void i(String tag, Object o) {
        if (IS_LOG)
            Log.i(tag, o == null ? "" : o.toString());
    }

    public static void w(String tag, Object o) {
        if (IS_LOG)
            Log.w(tag, o == null ? "" : o.toString());
    }

    public static void e(String tag, Object o) {
        if (IS_LOG)
            Log.e(tag, o == null ? "" : o.toString());
    }

    public static void d(Class<?> c, Object o) {
        if (IS_LOG)
            Log.d(c.getSimpleName(), o == null ? "" : o.toString());
    }

    public static void i(Class<?> c, Object o) {
        if (IS_LOG)
            Log.i(c.getSimpleName(), o == null ? "" : o.toString());
    }

    public static void w(Class<?> c, Object o) {
        if (IS_LOG)
            Log.w(c.getSimpleName(), o == null ? "" : o.toString());
    }

    public static void e(Class<?> c, Object o) {
        if (IS_LOG)
            Log.e(c.getSimpleName(), o == null ? "" : o.toString());
    }
}
