package com.qianmo.minepanel.Container;

import com.qianmo.minepanel.MinePanelDaemon;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;

public class ConsoleReader implements Runnable{
    private final InputStream inputStream;
    private final String ConsoleEncode;
    private final ContainerEntity containerEntity;

    public ConsoleReader(ContainerEntity containerEntity, String consoleEncode) {
        this.ConsoleEncode = consoleEncode;
        this.containerEntity = containerEntity;
        this.inputStream = containerEntity.getInputStream();
    }

    @Override
    public void run() {
        containerEntity.getConsoles().add("[Container] Container Started!");
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(this.inputStream, this.ConsoleEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String log;
            while ((log = bufferedReader.readLine()) != null) {
                containerEntity.getConsoles().add(StringEscapeUtils.unescapeJava(log));
                System.out.println(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
            MinePanelDaemon.getLogger().error(e.getMessage());
        }
    }
}
