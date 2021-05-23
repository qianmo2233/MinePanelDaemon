package com.qianmo.minepanel.Server.WebSocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnPool {
    private static Map<String, Integer> online = new HashMap<>();
    private static ConcurrentHashMap<String, WebSocketSession> sessionPools = new ConcurrentHashMap<>();

    public static Map<String, Integer> getOnline() {
        return online;
    }

    public static ConcurrentHashMap<String, WebSocketSession> getSessionPools() {
        return sessionPools;
    }
}
