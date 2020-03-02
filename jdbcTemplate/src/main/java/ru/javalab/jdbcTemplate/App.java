package ru.javalab.jdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.javalab.jdbcTemplate.models.Player;
import ru.javalab.jdbcTemplate.models.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.javalab.jdbcTemplate.repositories.PlayersRepository;
import ru.javalab.jdbcTemplate.repositories.PlayersRepositoryImpl;
import ru.javalab.jdbcTemplate.repositories.TeamsRepository;
import ru.javalab.jdbcTemplate.repositories.TeamsRepositoryImpl;

import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");



        Properties property = new Properties();
        FileInputStream fis;
        fis = new FileInputStream("C:/Users/pc/JavaLab/jdbcTemplate/src/main/resources/db.properties");
        property.load(fis);
        String dbUrl = property.getProperty("db.url");
        String dbUser = property.getProperty("db.user");
        String dbPassword = property.getProperty("db.password");

        HikariConfig config = new HikariConfig();
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.setJdbcUrl(dbUrl);
        HikariDataSource dataSource = new HikariDataSource(config);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        PlayersRepository playersRepository = new PlayersRepositoryImpl(jdbcTemplate);
        TeamsRepository teamsRepository = new TeamsRepositoryImpl(jdbcTemplate);
        Team team = Team.builder().name("Ak Bars")
                .hometown("Kazan")
                .build();
        teamsRepository.save(team);
        System.out.println(team.getId());
        teamsRepository.delete(2L);
        Optional<Team> team1 = teamsRepository.find(10L);
        System.out.println(team1.toString());
        System.out.println(team1.toString());
        Player player = Player.builder()
                .name("Rinat")
                .surname("Shigapov")
                .number(10)
                .team(team)
                .build();
        Player player2 = Player.builder()
                .name("Ivan")
                .surname("Ivanov")
                .number(4)
                .team(team)
                .build();

        playersRepository.save(player2);
        System.out.println(player2.getId());
        System.out.println(playersRepository.find(1L).toString());
        playersRepository.delete(3L);

        for (Player pl: playersRepository.findAll()) {
            System.out.println(pl.toString());
        }

        List<Team> teams = teamsRepository.findAll();
        for (Team t: teams) {
            System.out.println(t.toString());
        }

        System.out.println(teamsRepository.find(3L).toString());
        System.out.println(teamsRepository.find(100L).toString());

    }

}
