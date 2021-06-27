package com.qianmo.minepanel.Loader;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;

public class Initializer {
    public static void Init() throws Exception {
        File Config = new File("config/");
        File Server = new File("data/servers/");
        File Core = new File("data/cores/");
        File Template = new File("data/templates");
        File Data = new File("data/");
        File file = new File("config/application.yml");
        if(!Data.exists()) {
            Server.mkdirs();
            Core.mkdirs();
            Template.mkdirs();
        }
        if(!Config.exists()) {
            Config.mkdirs();
        }
        if(!file.exists()) {
            file.createNewFile();
            InputStream inputStream = new ClassPathResource("application.yml").getInputStream();
            FileUtils.copyToFile(inputStream, file);
            inputStream.close();
        }
    }
}
