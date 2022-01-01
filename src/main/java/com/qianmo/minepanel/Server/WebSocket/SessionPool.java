package com.qianmo.minepanel.Server.WebSocket;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionPool {
    private static BiMap<String, Integer> Sessions = HashBiMap.create();
    private static ConcurrentHashMap<String, Session> ConnPool = new ConcurrentHashMap<>();
    private static AtomicInteger onlineNum = new AtomicInteger();

    public static BiMap<String, Integer> getSessions() {
        return Sessions;
    }

    public static void setSessions(BiMap<String, Integer> sessions) {
        Sessions = sessions;
    }

    public static ConcurrentHashMap<String, Session> getConnPool() {
        return ConnPool;
    }

    public static void setConnPool(ConcurrentHashMap<String, Session> connPool) {
        ConnPool = connPool;
    }

    public static AtomicInteger getOnlineNum() {
        return onlineNum;
    }

    public static void setOnlineNum(AtomicInteger onlineNum) {
        SessionPool.onlineNum = onlineNum;
    }

    public static void addOnlineCount(){
        SessionPool.getOnlineNum().incrementAndGet();
    }

    public static void subOnlineCount() {
        SessionPool.getOnlineNum().decrementAndGet();
    }
}
