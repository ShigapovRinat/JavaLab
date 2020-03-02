package ru.javalab.jdbcTemplate.repositories;

import ru.javalab.jdbcTemplate.models.Player;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.javalab.jdbcTemplate.models.Team;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class PlayersRepositoryImpl implements PlayersRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select player.id, player.name, player.surname, player.number, player.team," +
            "       t.name as team_name, t.hometown from player" +
            "       left join team t on player.team = t.id where player.id = 5;";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from player";
    //language=SQL
    private static final String SQL_INSERT = "insert into player(name, surname, number, team) values (?, ?, ?, ?)";
    //language=SQL
    private static final String SQL_DELETE= "delete from player where id = ?";


    private JdbcTemplate jdbcTemplate;

    public PlayersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Player> playerRowMapper = (row, rowNumber) ->
            Player.builder()
                    .id(row.getLong("id"))
                    .name(row.getString("name"))
                    .surname(row.getString("surname"))
                    .number(row.getInt("number"))
                    .team(Team.builder()
                            .id(row.getLong("team"))
                            .name(row.getString("team_name"))
                            .hometown(row.getString("hometown"))
                            .build())
                    .build();

    @Override
    public Optional<Player> find(Long id) {
        try {
            Player player = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, playerRowMapper);
            return Optional.ofNullable(player);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Player> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, playerRowMapper);
    }

    @Override
    public void save(Player player) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, player.getName());
            statement.setString(2, player.getSurname());
            statement.setInt(3, player.getNumber());
            statement.setLong(4, player.getTeam().getId());
            return statement;
        }, keyHolder);
        player.setId((Long)keyHolder.getKey());
    }


    @Override
    public void delete(Long id) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            return statement;
        });
    }
}
