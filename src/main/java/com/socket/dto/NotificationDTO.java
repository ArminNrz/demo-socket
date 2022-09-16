package com.socket.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socket.entity.ProduceType;
import lombok.Data;

@Data
public class NotificationDTO {
    private String messageContent;
    private String userId;
    private ProduceType produceType;
    @JsonIgnore
    private String id;
}
