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

    public static String stringFilter(String string) {
        if (string != null && string.length() > 0) {
            char[] contentCharArr = string.toCharArray();
            for (int i = 0; i < contentCharArr.length; i++) {
                if (contentCharArr[i] < 0x20 || contentCharArr[i] == 0x7F) {
                    contentCharArr[i] = 0x20;
                }
            }
            return new String(contentCharArr);
        }
        return "";
    }
}
