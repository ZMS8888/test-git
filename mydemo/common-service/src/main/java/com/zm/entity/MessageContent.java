package com.zm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@Builder
@ToString
@AllArgsConstructor
public class MessageContent {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    /**
     * 发送消息体
     */
    private Object message;
    /**
     * 消息类型
     */
    private String messageType;
}
