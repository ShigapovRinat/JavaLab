import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.Player;
import models.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import repositories.PlayersRepository;
import repositories.PlayersRepositoryImpl;
import repositories.TeamsRepository;
import repositories.TeamsRepositoryImpl;

import java.io.FileInputStream;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class App {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/javalab_jdbcTemplate";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUsername(DB_USER);
//        dataSource.setPassword(DB_PASSWORD);
//        dataSource.setUrl(DB_URL);


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
                .teamId(3L)
                .build();
        Player player2 = Player.builder()
                .name("Ivan")
                .surname("Ivanov")
                .number(4)
                .teamId(3L)
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

    }

}
