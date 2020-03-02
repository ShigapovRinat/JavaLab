package ru.javalab.jdbcTemplate.repositories;

import ru.javalab.jdbcTemplate.models.Player;
import ru.javalab.jdbcTemplate.models.Team;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.*;

public class TeamsRepositoryImpl implements TeamsRepository {

    //language=SQL
    private static final String SQL_INSERT = "insert into team(name, hometown) values (?, ?)";
    //language=SQL
    private static final String SQL_DELETE = "delete from team where id = ?";
    //language=SQL
    private static final String SQL_SELECT_BY_ID_WITH_PLAYERS = "select public.team.id as team_id, team.name as team_name,\n" +
            "team.hometown, p.id as player_id, p.name as player_name, surname, number\n" +
            "from team left join player p on team.id = p.team where team.id = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL_WITH_PLAYERS = "select public.team.id as team_id, team.name as team_name,\n" +
            "team.hometown, p.id as player_id, p.name as player_name, surname, number\n" +
            "from team left join player p on team.id = p.team";

    private JdbcTemplate jdbcTemplate;

    public TeamsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Team team) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, team.getName());
            statement.setString(2, team.getHometown());
            return statement;
        }, keyHolder);

        team.setId((Long) keyHolder.getKey());
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

    @Override
    public Optional<Team> find(Long id) {
        try {
            List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(SQL_SELECT_BY_ID_WITH_PLAYERS, id);
            Map<String, Object> m = resultSet.get(0);
            Team team = Team.builder()
                    .id((Long) m.get("team_id"))
                    .name((String) m.get("team_name"))
                    .hometown((String) m.get("hometown"))
                    .build();
            Player player;
            List<Player> players = new ArrayList<>();
            for (Map<String, Object> map : resultSet) {
                player = Player.builder()
                        .id((Long) map.get("player_id"))
                        .name((String) map.get("player_name"))
                        .surname((String) map.get("surname"))
                        .number((Integer) map.get("number"))
                        .team(team)
                        .build();
                players.add(player);
            }
            team.setPlayers(players);

            return Optional.ofNullable(team);
        } catch (EmptyResultDataAccessException| IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(SQL_SELECT_ALL_WITH_PLAYERS);
        Team team = new Team();
        Team newTeam = new Team();
        Player player;
        List<Player> players = new ArrayList<>();
        Map<String, Object> lastMap = new HashMap<>();
        lastMap.put("team_id", -1);

        for (Map<String, Object> map : resultSet) {
            if (!map.get("team_id").equals(lastMap.get("team_id"))) {
                newTeam = Team.builder()
                        .id((Long) map.get("team_id"))
                        .name((String) map.get("team_name"))
                        .hometown((String) map.get("hometown"))
                        .build();
                team = newTeam;
                players = new ArrayList<>();
            }

            player = Player.builder()
                    .id((Long) map.get("player_id"))
                    .name((String) map.get("player_name"))
                    .surname((String) map.get("surname"))
                    .number((Integer) map.get("number"))
                    .team(newTeam)
                    .build();
            players.add(player);


            if (!map.get("team_id").equals(lastMap.get("team_id")) && !map.get("team_id").equals(-1)) {
                team.setPlayers(players);
                teams.add(team);
            }

            lastMap = map;
        }
        return teams;
    }


//    //language=SQL
//    private static final String SQL_SELECT_BY_ID = "select * from team where id = ?";
//    //language=SQL
//    private static final String SQL_SELECT_ALL = "select * from team";
//    //language=SQL
//    private static final String SQL_SELECT_PLAYER_BY_ID_TEAM = "select * from player where team = ?";
//        private RowMapper<Team> teamRowMapper = (row, rowNumber) ->
//            Team.builder()
//                    .id(row.getLong("id"))
//                    .name(row.getString("name"))
//                    .hometown(row.getString("hometown"))
//                    .build();

//    private Optional<List<Player>> findPlayersTeam(Long idTeam) {
//        try {
//            List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(SQL_SELECT_PLAYER_BY_ID_TEAM, idTeam);
//            List<Player> players = new ArrayList<>();
//            Player player;
//            for (Map<String, Object> map : resultSet) {
//                player = Player.builder()
//                        .id((Long) map.get("id"))
//                        .name((String) map.get("name"))
//                        .surname((String) map.get("surname"))
//                        .number((Integer) map.get("number"))
//                        .teamId(idTeam)
//                        .build();
//                players.add(player);
//            }
//            return Optional.ofNullable(players);
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }

//    @Override
//    public Optional<Team> find(Long id) {
//        try {
//            Team team = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, teamRowMapper);
//            if (findPlayersTeam(id).isPresent()) {
//                team.setPlayers(findPlayersTeam(id).get());
//            }
//            return Optional.ofNullable(team);
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }

//    @Override
//    public List<Team> findAll() {
//        List<Team> teams = jdbcTemplate.query(SQL_SELECT_ALL, teamRowMapper);
//        for (Team team: teams) {
//            team.setPlayers(findPlayersTeam(team.getId()).get());
//        }
//        return teams;
//    }

}
