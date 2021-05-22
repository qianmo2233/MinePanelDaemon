package com.qianmo.minepanel;

import com.qianmo.minepanel.Utils.ConsoleReader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ContainerManager {
    private static Map<Integer, Process> containers = new HashMap<>();
    private static Map<Integer, String> consoles = new HashMap<>();
    private static Map<Integer, OutputStream> outputStreamMap = new HashMap<>();
    private static Map<Integer, InputStream> inputStreamMap = new HashMap<>();

    private final Runtime runtime = Runtime.getRuntime();

    public void create(Integer id, String cmd, String[] args) {
        Process process;
        try {
            process = runtime.exec(cmd, args, new File("data/servers/" + id + "/"));
        } catch (IOException e) {
            e.printStackTrace();
            MinePanelApplication.getLogger().error("Failed starting Container!");
            return;
        }
        containers.put(id, process);
        final InputStream inputStream = process.getInputStream();
        final OutputStream outputStream = process.getOutputStream();
        outputStreamMap.put(id, outputStream);
        inputStreamMap.put(id, inputStream);
        Thread thread = new Thread(new ConsoleReader(id, "UTF-8"));
        thread.start();
    }

    public void destroy(Integer id) {
        consoles.remove(id);
        containers.get(id).destroyForcibly();
        containers.remove(id);
        try {
            inputStreamMap.get(id).close();
            outputStreamMap.get(id).close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        inputStreamMap.remove(id);
        outputStreamMap.remove(id);
    }

    public static Map<Integer, Process> getContainers() {
        return containers;
    }

    public static Map<Integer, String> getConsoles() {
        return consoles;
    }

    public static Map<Integer, OutputStream> getOutputStreamMap() {
        return outputStreamMap;
    }

    public static Map<Integer, InputStream> getInputStreamMap() {
        return inputStreamMap;
    }
}
