package com.qianmo.minepanel;

import com.qianmo.minepanel.Utils.ConsoleReader;
import com.qianmo.minepanel.Utils.StatusListener;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ContainerManager {
    private static Map<Integer, Process> containers = new HashMap<>();
    private static Map<Integer, String> consoles = new HashMap<>();
    private static Map<Integer, OutputStream> outputStreamMap = new HashMap<>();
    private static Map<Integer, InputStream> inputStreamMap = new HashMap<>();

    private static final Runtime runtime = Runtime.getRuntime();

    public static void create(Integer id, String cmd, String[] args) {
        Process process;
        try {
            process = runtime.exec(cmd, args, new File("data/servers/" + id + "/"));
        } catch (Exception e) {
            e.printStackTrace();
            MinePanelApplication.getLogger().error("Container start failed!");
            return;
        }
        containers.put(id, process);
        final InputStream inputStream = process.getInputStream();
        final OutputStream outputStream = process.getOutputStream();
        outputStreamMap.put(id, outputStream);
        inputStreamMap.put(id, inputStream);
        new Thread(new ConsoleReader(id, "GBK")).start();
        new Thread(new StatusListener(process, id)).start();
        MinePanelApplication.getLogger().info("Container started");
    }

    public static void destroy(Integer id) {
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

    public static void execute(Integer id, String cmd) {
        cmd = cmd + "\n";
        try {
            outputStreamMap.get(id).write(cmd.getBytes(StandardCharsets.UTF_8));
            outputStreamMap.get(id).flush();
        } catch (IOException e) {
            e.printStackTrace();
            MinePanelApplication.getLogger().error("Execute Error");
        }
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
