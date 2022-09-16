package com.socket.dto.channel;

import lombok.Data;

@Data
public class ChannelOneMessage {
    private String content;
    private long messageId;
}
