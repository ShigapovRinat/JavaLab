package ru.javalab.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javalab.chat.models.Chat;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {
    private Long id;
    private String name;
    private String password;
    private Long userId;

    public static ChatDto from(Chat chat){
        return ChatDto.builder()
                .id(chat.getId())
                .userId(chat.getCreator().getId())
                .name(chat.getName())
                .password(chat.getPassword())
                .build();
    }

    public static List<ChatDto> from(List<Chat> chats){
        return chats.stream()
                .map(ChatDto::from)
                .collect(Collectors.toList());
    }
}