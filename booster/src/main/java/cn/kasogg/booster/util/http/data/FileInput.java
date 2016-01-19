package cn.kasogg.booster.util.http.data;

import java.io.File;

/**
 * Author: KasoGG
 * Date:   2016-01-19 13:33
 */
public class FileInput {
    public String key;
    public String fileName;
    public File file;

    public FileInput(String key, String fileName, File file) {
        this.key = key;
        this.fileName = fileName;
        this.file = file;
    }
}
