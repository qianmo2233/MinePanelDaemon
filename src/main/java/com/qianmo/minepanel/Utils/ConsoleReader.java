package com.qianmo.minepanel.Utils;

import com.qianmo.minepanel.MinePanelApplication;
import com.qianmo.minepanel.ContainerManager;

import java.io.*;

public class ConsoleReader implements Runnable{
    private final InputStream inputStream;
    private final String ConsoleEncode;
    private final Integer id;

    public ConsoleReader(Integer id, String consoleEncode) {
        this.ConsoleEncode = consoleEncode;
        this.id = id;
        this.inputStream = ContainerManager.getInputStreamMap().get(this.id);
    }

    @Override
    public void run() {
        ContainerManager.getConsoles().put(id, "");
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
                String logs = ContainerManager.getConsoles().get(id);
                logs += log + "\n";
                ContainerManager.getConsoles().replace(id, logs);
                System.out.println(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
            MinePanelApplication.getLogger().error(e.getMessage());
        }
    }
}
