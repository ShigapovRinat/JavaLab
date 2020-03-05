package ru.javalab.registration.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.javalab.registration.models.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from \"user\"";
    //language=SQL
    private static final String SQL_SELECT_BY_USERNAME = "select * from \"user\" where username = ?";
    //language=SQL
    private static final String SQL_INSERT = "insert into \"user\"(username, email, password, confirm_link, is_confirmed) values (?,?,?,?,?)";
    //language=SQL
    private static final String SQL_DELETE = "delete from \"user\" where username = ?";
    //language=SQL
    private static final String SQL_SELECT_BY_CONFIRM_LINK = "select * from \"user\" where confirm_link = ?";
    //language=SQL
    private static final String SQL_UPDATE = "update \"user\" set is_confirmed = true where username = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
                    .username(row.getString("username"))
                    .email(row.getString("email"))
                    .password(row.getString("password"))
                    .confirmLink(row.getString("confirm_link"))
                    .isConfirmed(row.getBoolean("is_confirmed"))
                    .build();


    @Override
    public Optional<User> find(String username) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_USERNAME, new Object[]{username}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getConfirmLink());
            statement.setBoolean(5, user.isConfirmed());
            return statement;
        }, keyHolder);
        user.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(String username) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_DELETE);
            statement.setString(1, username);
            return statement;
        });
    }

    @Override
    public void confirmed(String username) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_UPDATE);
            statement.setString(1, username);
            return statement;
        });
    }


    @Override
    public Optional<User> findByConfirmLink(String confirmLink) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_CONFIRM_LINK, new Object[]{confirmLink}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
