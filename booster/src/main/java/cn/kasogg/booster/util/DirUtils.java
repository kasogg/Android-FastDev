package cn.kasogg.booster.util;

import android.os.Environment;

import java.io.File;

/**
 * Author: KasoGG
 * Date:   2015-09-10 20:24
 */
public class DirUtils {
    private DirUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String APP_PATH = "/cbms";

    public static String getCapturePhotoFilePath(String fileName) {
        File file = new File(getCapturePhotoDirPath(), fileName);
        if (file.exists()) {
            file.delete();
        }
        return file.getPath();
    }

    public static String getCapturePhotoDirPath() {
        File sdcardDir = Environment.getExternalStorageDirectory();
        File fileDirectory = new File("/mnt/sdcard");
        if (null != sdcardDir) {
            fileDirectory = sdcardDir;
        }
        File dir = new File(fileDirectory + APP_PATH + "/photos/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

    public static String getCachePhotoFilePath(String fileName) {
        File file = new File(getCachePhotoDirPath(), fileName);
        if (file.exists()) {
            file.delete();
        }
        return file.getPath();
    }

    public static String getCachePhotoDirPath() {
        File sdcardDir = Environment.getExternalStorageDirectory();
        File fileDirectory = new File("/mnt/sdcard");
        if (null != sdcardDir) {
            fileDirectory = sdcardDir;
        }
        File dir = new File(fileDirectory + APP_PATH + "/cache/photos/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

    public static String getDownloadDirPath() {
        File sdcardDir = Environment.getExternalStorageDirectory();
        File fileDirectory = new File("/mnt/sdcard");
        if (null != sdcardDir) {
            fileDirectory = sdcardDir;
        }
        File dir = new File(fileDirectory + APP_PATH + "/downloads/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }
}
