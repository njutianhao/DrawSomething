package com.th.drawsomething;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;


@Component
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    public WebSocketHandshakeInterceptor() {
        super(List.of("userName","roomId"));
    }
}
