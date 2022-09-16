package com.socket.service;

import com.socket.dto.NotificationDTO;
import com.socket.dto.channel.ChannelOneMessage;
import com.socket.dto.global.GlobalMessage;
import com.socket.dto.privateMessage.PrivateMessage;
import com.socket.entity.ProduceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final SessionIdUserHandlerService sessionHandler;
    private final NotificationEntityService entityService;

    public void sendGlobalNotification(long messageId, String message) {
        log.debug("Produce global notification, message: {}", message);
        GlobalMessage dto = new GlobalMessage();
        dto.setContent(message);
        dto.setMessageId(messageId);
        messagingTemplate.convertAndSend("/topic/global-notifications", dto);
    }

    public void sendChannelOneNotification(long messageId, String message) {
        log.debug("Produce channel one notification, message: {}", message);
        ChannelOneMessage dto = new ChannelOneMessage();
        dto.setContent(message);
        dto.setMessageId(messageId);
        messagingTemplate.convertAndSend("/topic/ch1-notification", dto);
    }

    public void getFormerNotification(String userId) {
        entityService.findFormerNotification(userId)
                .forEach(privateMessage -> this.sendPrivateMessage(userId, privateMessage.getContent(), privateMessage.getMessageId()));
    }

    public void sendPrivateMessage(String userId, String message, long messageId) {
        log.debug("Produce private notification, to: {}, message: {}", userId, message);
        PrivateMessage privateMessageDTO = new PrivateMessage();
        String sessionId = sessionHandler.getSessionIdFromUserId(userId);
        if (sessionId == null) {
            log.warn("No session exist for userId: {}", userId);
            return;
        }
        privateMessageDTO.setContent(message);
        privateMessageDTO.setMessageId(messageId);
        messagingTemplate.convertAndSendToUser(sessionId, "/topic/private-messages", privateMessageDTO);
    }

    public void produceNotification(NotificationDTO notificationDTO) {
        long messageId = entityService.save(notificationDTO);

        ProduceType produceType = notificationDTO.getProduceType();

        switch (produceType) {
            case GLOBAL:
                this.sendGlobalNotification(messageId, notificationDTO.getMessageContent());
                break;
            case CHANNEL_ONE:
                this.sendChannelOneNotification(messageId, notificationDTO.getMessageContent());
                break;
            case PRIVATE:
                this.sendPrivateMessage(notificationDTO.getUserId(), notificationDTO.getMessageContent(), messageId);
                break;
            default:
                log.warn("ProduceType is not define");
        }
    }

    public void makeSeen(long notificationId) {
        entityService.makeSeen(notificationId);
    }
}
