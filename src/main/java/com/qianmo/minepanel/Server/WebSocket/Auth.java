package com.qianmo.minepanel.Server.WebSocket;

import java.util.HashMap;
import java.util.Map;

public class Auth {
    private static Map<String, Integer> AuthMap = new HashMap<>();

    public static Map<String, Integer> getAuthMap() {
        return AuthMap;
    }
}
