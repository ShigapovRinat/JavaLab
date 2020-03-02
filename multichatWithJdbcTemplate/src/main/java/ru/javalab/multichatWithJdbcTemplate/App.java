package ru.javalab.multichatWithJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.javalab.multichatWithJdbcTemplate.repositories.MessagesRepository;
import ru.javalab.multichatWithJdbcTemplate.repositories.MessagesRepositoryImpl;
import ru.javalab.multichatWithJdbcTemplate.repositories.UsersRepository;
import ru.javalab.multichatWithJdbcTemplate.repositories.UsersRepositoryImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws Exception {


        Class.forName("org.postgresql.Driver");
        Properties property = new Properties();
        FileInputStream fis;
        fis = new FileInputStream("C:/Users/pc/JavaLab/multichatWithJdbcTemplate/src/main/resources/db.properties");
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
        UsersRepository usersRepository = new UsersRepositoryImpl(jdbcTemplate);
        MessagesRepository messagesRepository = new MessagesRepositoryImpl(jdbcTemplate);
        System.out.println(usersRepository.findAll());
    }
}
