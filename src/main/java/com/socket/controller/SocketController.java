package com.socket.controller;

import com.socket.dto.MakeSeenNotificationDTO;
import com.socket.dto.UserDTO;
import com.socket.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SocketController {

    private final NotificationService notificationService;

    @MessageMapping("/former-notification")
    public void getFormerMessage(UserDTO userDTO) {
        log.info("Websocket request to get former message, from: {}", userDTO.getUser());
        notificationService.getFormerNotification(userDTO.getUser());
    }

    @MessageMapping("/make-seen")
    public void makeSeenNotification(MakeSeenNotificationDTO dto) {
        log.info("Websocket request to make seen notification: {}", dto.getNotificationId());
        notificationService.makeSeen(dto.getNotificationId());
    }
}
