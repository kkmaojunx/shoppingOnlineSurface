package com.example.shop.socket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

    private static Integer onLineCount = 0;         // 当前在线数

    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    private static ConcurrentHashMap<String, WebSocketServer> concurrentHashMap = new ConcurrentHashMap<>();

    private Session session;

    /**
     * 打开连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        concurrentHashMap.put(session.getId(), this);
        System.out.println("现在的SessionId："+session.getId());
        addOnLineCount();
        System.out.println("有新增连接，当前连接数为：" + getOnLineCount());
        try {
            sendMessage("连接成功,sessionId:"+session.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        sendInfo(message);
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnLineCount();
        System.out.println("有一个连接退出，当前连接数为：" + getOnLineCount());
    }

    /**
     * 错误信息
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 指定人发送消息
     * @param message
     * @param toSessionId
     */
    public static void sendOne(String message, String toSessionId) {
        WebSocketServer webSocketServer = concurrentHashMap.get(toSessionId);
        try {
            webSocketServer.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     * @param message
     */
    public static void sendInfo(String message) {
        for (WebSocketServer w :
                webSocketSet) {
            try {
                w.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();continue;

            }
        }
    }

    public static synchronized int getOnLineCount() {
        return onLineCount;
    }

    public static synchronized void addOnLineCount() {
        onLineCount++;
    }

    public static synchronized void subOnLineCount() {
        onLineCount--;
    }
}
