package ru.javalab.multichatWithJdbcTemplate.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Getter
    @Setter
    private Long id;
    @Getter
    private User user;
    @Getter
    private String text;
}
