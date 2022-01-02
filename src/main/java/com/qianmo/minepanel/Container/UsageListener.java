package com.qianmo.minepanel.Container;

import com.qianmo.minepanel.Server.WebSocket.SessionPool;
import com.qianmo.minepanel.Server.WebSocket.WebSocketServer;

import java.io.*;
import java.util.Objects;
import java.util.regex.Pattern;

public class UsageListener implements Runnable {

    private final Runtime runtime;
    private final Long pid;
    private final Integer id;

    public UsageListener(Long pid, Integer id) {
        this.runtime = Runtime.getRuntime();
        this.pid = pid;
        this.id = id;
    }

    @Override
    public void run() {
        while (ContainerManager.getContainer().containsKey(id)) {
            Process process;
            try {
                process = runtime.exec("tasklist /fi \"PID eq " + pid + "\"");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            InputStreamReader inputStreamReader;
            try {
                inputStreamReader = new InputStreamReader(process.getInputStream(), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(Objects.requireNonNull(inputStreamReader));
            try {
                String log;
                while ((log = bufferedReader.readLine()) != null) {
                    if (log.contains("K")) {
                        String memory = log.substring(log.length() - 12);
                        memory = memory.replace("K", "");
                        memory = memory.replace(" ", "");
                        memory = memory.replace(",", "");
                        if (SessionPool.getSessions().containsValue(id))
                            WebSocketServer.sendInfo(SessionPool.getSessions().inverse().get(id), "{\"type\":\"memory\",\"msg\":\"" + memory + "\"}");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        process.destroy();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
