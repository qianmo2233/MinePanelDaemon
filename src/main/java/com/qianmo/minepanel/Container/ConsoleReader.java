package com.qianmo.minepanel.Container;

import com.qianmo.minepanel.Server.WebSocket.SessionPool;
import com.qianmo.minepanel.Server.WebSocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;

@Slf4j
public class ConsoleReader implements Runnable{
    private final InputStream inputStream;
    private final String ConsoleEncode;
    private final ContainerEntity containerEntity;
    private final Integer id;

    public ConsoleReader(ContainerEntity containerEntity, String consoleEncode, Integer id) {
        this.ConsoleEncode = consoleEncode;
        this.containerEntity = containerEntity;
        this.inputStream = containerEntity.getInputStream();
        this.id = id;
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
                //System.out.println(log);
                if (SessionPool.getSessions().containsValue(id)) {
                    WebSocketServer.sendInfo(SessionPool.getSessions().inverse().get(id), "{\"type\":\"log\",\"msg\":\"" + log + "\"}");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
