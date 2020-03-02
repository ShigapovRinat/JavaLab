package ru.javalab.jdbcTemplate.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
    private Long id;
    private String name;
    private List<Player> players;
    private String hometown;
}
