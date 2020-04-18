package ru.javalab.chatWebSocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javalab.chatWebSocket.models.Message;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private String text;
    private String user_login;
    private Long user_id;
    private Long chat_id;

    public static MessageDto from(Message message){
        return MessageDto.builder()
                .id(message.getId())
                .text(message.getText())
                .chat_id(message.getChat().getId())
                .user_id(message.getCreator().getId())
                .user_login(message.getCreator().getLogin())
                .build();
    }

    public static List<MessageDto> from(List<Message> messages){
        return messages.stream()
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }
}