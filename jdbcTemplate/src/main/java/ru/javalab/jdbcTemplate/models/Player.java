package ru.javalab.jdbcTemplate.models;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    @Getter
    @Setter
    private Long id;
    @Getter
    private String name;
    @Getter
    private String surname;
    @Getter
    private Integer number;
    @Getter
    private Team team;
}
