package com.socket.config;

import com.socket.service.SessionIdUserHandlerService;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    private final SessionIdUserHandlerService sessionHandler;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        String sessionId = attributes.get("sessionId").toString();

        log.debug("Request for handshake with sessionId: {}", sessionId);
        sessionHandler.persist(sessionId, "");
        log.debug("Register user with cookieId: {}", sessionId);
        return new UserPrincipal(sessionId);
    }
}
