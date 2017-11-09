package com.zhz.smart.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by zz1987 on 17/11/2.
 */
@ServerEndpoint(value="/websocket")
@Component
public class MyWebsocket {

    @OnOpen
    public void onOpen(Session session){
        try {
            session.getBasicRemote().sendText("欢迎光临");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(){
        System.out.println("欢迎再来");
    }

    @OnMessage
    public void onMessage(String message,Session session){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session,Throwable error){
        error.printStackTrace();
    }

}
