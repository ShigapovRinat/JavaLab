package ru.javalab.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
