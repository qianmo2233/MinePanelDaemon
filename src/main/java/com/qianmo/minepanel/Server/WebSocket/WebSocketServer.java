package com.qianmo.minepanel.Server.WebSocket;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
@Component
public class WebSocketServer {
}
