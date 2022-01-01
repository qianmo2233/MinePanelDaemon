package com.qianmo.minepanel.Container;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ContainerManager {
    private static Map<Integer, ContainerEntity> Container = new HashMap<>();

    private static final Runtime runtime = Runtime.getRuntime();

    public static void create(Integer id, String cmd, String[] args, String container) {
        Process process;
        try {
            process = runtime.exec(cmd, args, new File("data/servers/" + id + "/"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Container start failed!");
            return;
        }
        final InputStream inputStream = process.getInputStream();
        final OutputStream outputStream = process.getOutputStream();
        ContainerEntity containerEntity = new ContainerEntity(container, process, inputStream, outputStream);
        Container.put(id, containerEntity);
        new Thread(new ConsoleReader(containerEntity, "UTF-8", id)).start();
        new Thread(new StatusListener(process, id)).start();
        log.info("Container started");
    }

    public static void destroy(Integer id) {
        ContainerEntity containerEntity = Container.get(id);
        try {
            containerEntity.getProcess().destroyForcibly();
            containerEntity.getInputStream().close();
            containerEntity.getOutputStream().close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        Container.remove(id);
    }

    public static void execute(Integer id, String cmd) {
        cmd = cmd + "\n";
        try {
            Container.get(id).getOutputStream().write(cmd.getBytes(StandardCharsets.UTF_8));
            Container.get(id).getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Execute Error");
        }
    }

    public static Map<Integer, ContainerEntity> getContainer() {
        return Container;
    }
}
