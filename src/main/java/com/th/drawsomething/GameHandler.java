package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class GameHandler extends TextWebSocketHandler {

    @Autowired
    private GameManager gameManager;

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer roomId = (Integer)session.getAttributes().get("roomId");
        String userName = (String)session.getAttributes().get("userName");
        if(roomId == null || userName == null)
        {
            //session.close();
            //return ;
        }
        gameManager.getGames().get(roomId).addPlayer(userName);
    }

    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            this.handleTextMessage(session, (TextMessage)message);
        } else if (message instanceof BinaryMessage) {
            this.handleBinaryMessage(session, (BinaryMessage)message);
        } else {
            if (!(message instanceof PongMessage)) {
                throw new IllegalStateException("Unexpected WebSocket message type: " + message);
            }

            this.handlePongMessage(session, (PongMessage)message);
        }
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection Closed");
    }
}
