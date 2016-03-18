package com.kasogg.booster.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static int getAppVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        int versionCode = 0;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getAppVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String versionCode = null;
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName componentName = taskList.get(0).topActivity;
            if (componentName != null && componentName.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static String getFirstInstallTime(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String firstInstallTimeString = "";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            long firstInstallTime = packageInfo.firstInstallTime;//应用第一次安装的时间

            firstInstallTimeString = TimeUtils.getTime(firstInstallTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firstInstallTimeString;
    }
}
