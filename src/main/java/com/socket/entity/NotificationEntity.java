package com.socket.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message_content", nullable = false)
    private String messageContent;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "produce_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProduceType produceType;

    @Column(name = "seen", nullable = false)
    private boolean seen = false;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "created", nullable = false)
    private ZonedDateTime created;
}
