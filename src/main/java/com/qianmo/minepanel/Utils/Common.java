package com.qianmo.minepanel.Utils;

import java.io.File;

public class Common {
    public static void deleteAll(String path) {
        File filePar = new File(path);
        if (filePar.exists()) {
            File[] files = filePar.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    deleteAll(file.getAbsolutePath());
                    file.delete();
                }
            }
            filePar.delete();
        }
    }
}
