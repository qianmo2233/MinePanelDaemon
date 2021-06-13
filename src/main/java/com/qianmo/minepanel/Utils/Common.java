package com.qianmo.minepanel.Utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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

    public static boolean compareFile(File f1, File f2) {
        try {
            InputStream i1 = new FileInputStream(f1);
            InputStream i2 = new FileInputStream(f2);
            String s1 = DigestUtils.md5Hex(IOUtils.toByteArray(i1));
            String s2 = DigestUtils.md5Hex(IOUtils.toByteArray(i2));
            i1.close();
            i2.close();
            return s1.equals(s2);
        } catch (Exception e) {
            return false;
        }
    }
}
