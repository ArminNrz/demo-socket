package com.socket.service;

import com.socket.dto.NotificationDTO;
import com.socket.dto.privateMessage.PrivateMessage;
import com.socket.entity.NotificationEntity;
import com.socket.repository.NotificationEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationEntityService {

    private final NotificationEntityRepository repository;

    public long save(NotificationDTO notificationDTO) {
        NotificationEntity entity = new NotificationEntity();
        entity.setMessageContent(notificationDTO.getMessageContent());
        entity.setUserId(notificationDTO.getUserId());
        entity.setProduceType(notificationDTO.getProduceType());
        entity.setSeen(false);
        entity.setDeleted(false);
        entity.setCreated(ZonedDateTime.now());
        repository.save(entity);
        return entity.getId();
    }

    public List<PrivateMessage> findFormerNotification(String userId) {
        return repository.findAllByUserId(userId).stream()
                .filter(notificationEntity -> !notificationEntity.isDeleted())
                .filter(notificationEntity -> !notificationEntity.isSeen())
                .peek(notificationEntity -> log.debug("Found notificationId: {}, for userId: {}", notificationEntity.getId(), userId))
                .map(notificationEntity -> new PrivateMessage(notificationEntity.getMessageContent(), notificationEntity.getId()))
                .collect(Collectors.toList());
    }

    public void makeSeen(long notificationId) {
        repository.findById(notificationId).ifPresentOrElse(
                notificationEntity -> {
                    notificationEntity.setSeen(true);
                    repository.save(notificationEntity);
                },
                () -> {
                    log.warn("Can not found notification with id: {}", notificationId);
                }
        );
    }
}
