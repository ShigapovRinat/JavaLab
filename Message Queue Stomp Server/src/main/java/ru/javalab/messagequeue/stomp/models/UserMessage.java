package ru.javalab.messagequeue.stomp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserMessage {
    String username;
    String realname;
    String email;
    String queueName;
}