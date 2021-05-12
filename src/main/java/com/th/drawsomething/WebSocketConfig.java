package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;


    @Autowired
    GameHandler gameHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(gameHandler, "/game").
                setAllowedOriginPatterns("*").
                addInterceptors(webSocketHandshakeInterceptor);
    }

}