package com.socket.repository;

import com.socket.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByUserId(String userId);
}
