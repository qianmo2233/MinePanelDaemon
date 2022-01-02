package com.qianmo.minepanel.Server.WebSocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    //发送消息
    public static void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }
    //给指定用户发送信息
    public static void sendInfo(String sid, String message){
        Session session = SessionPool.getConnPool().get(sid);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String sid){
        SessionPool.getConnPool().put(sid, session);
        SessionPool.addOnlineCount();
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "sid") String sid){
        SessionPool.getConnPool().remove(sid);
        SessionPool.subOnlineCount();
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message) throws IOException{
        //
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        throwable.printStackTrace();
    }
}
