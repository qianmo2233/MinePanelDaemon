package com.qianmo.minepanel.Container;

import com.qianmo.minepanel.Server.WebSocket.SessionPool;
import com.qianmo.minepanel.Server.WebSocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record StatusListener(Process process, Integer id) implements Runnable {

    @Override
    public void run() {
        try {
            if (SessionPool.getSessions().containsValue(id))
                WebSocketServer.sendInfo(SessionPool.getSessions().inverse().get(id), "{\"type\":\"status\",\"msg\":\"running\"}");
            process.pid();
            int code = process.waitFor();
            if (code != 0) {
                log.warn("Container " + id + " has crashed or killed");
            } else {
                log.info("Container " + id + " has stopped");
            }
            if (ContainerManager.getContainer().containsKey(id)) {
                ContainerManager.destroy(id);
            }
            if (SessionPool.getSessions().containsValue(id))
                WebSocketServer.sendInfo(SessionPool.getSessions().inverse().get(id), "{\"type\":\"status\",\"msg\":\"stopped\"}");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
