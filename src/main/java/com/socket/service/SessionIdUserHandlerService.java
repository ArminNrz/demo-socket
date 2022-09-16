package com.socket.service;

import com.google.common.collect.HashBiMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SessionIdUserHandlerService {

    private final HashBiMap<String, String> sessionIdUserIdMap = HashBiMap.create();

    public void persist(String sessionId, String userId) {
        sessionIdUserIdMap.put(sessionId, userId);
        log.info("Cached: {}", sessionIdUserIdMap);
    }

    public String getSessionIdFromUserId(String userId) {
        return sessionIdUserIdMap.inverse().get(userId);
    }
}
