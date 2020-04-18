package ru.javalab.chatWebSocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javalab.chatWebSocket.models.Message;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseInviteChatDto {
    private Long id;
    private String name;
    private List<MessageDto> messages;
}
