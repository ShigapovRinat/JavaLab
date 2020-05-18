package ru.javalab.messagequeue.stomp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMessage {
    private String status;
    private String content;
    private String messageId;
}