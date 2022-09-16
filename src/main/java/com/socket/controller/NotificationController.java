package com.socket.controller;

import com.socket.dto.NotificationDTO;
import com.socket.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/produce")
    public void produceMessage(@RequestBody NotificationDTO notificationDTO) {
        log.info("REST request to produce notification: {}", notificationDTO);
        notificationService.produceNotification(notificationDTO);
    }
}
