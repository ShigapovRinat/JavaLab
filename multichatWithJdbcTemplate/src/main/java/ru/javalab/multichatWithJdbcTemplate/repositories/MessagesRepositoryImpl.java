package ru.javalab.multichatWithJdbcTemplate.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.javalab.multichatWithJdbcTemplate.models.Message;
import ru.javalab.multichatWithJdbcTemplate.models.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class MessagesRepositoryImpl implements MessagesRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select message.id_message, message.text" +
            "       u.login,message.id_user from message" +
            "       left join \"user\" u on message.id_user = u.id where message.id_message = ?;";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from message";
    //language=SQL
    private static final String SQL_INSERT = "insert into message(text, id_user) values (?, ?)";
    //language=SQL
    private static final String SQL_DELETE = "delete from message where id_message = ?";

    private JdbcTemplate jdbcTemplate;

    public MessagesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Message> messageRowMapper = (row, rowNumber) ->
            Message.builder()
                    .id(row.getLong("id_message"))
                    .text(row.getString("text"))
                    .user(User.builder()
                            .id(row.getLong("id_user"))
                            .login(row.getString("login"))
                            .build())
                    .build();

    @Override
    public Optional<Message> find(Long id) {
        try {
            Message message = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, messageRowMapper);
            return Optional.ofNullable(message);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll()  {
        return jdbcTemplate.query(SQL_SELECT_ALL, messageRowMapper);
    }

    @Override
    public void save(Message message) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT, new String[]{"id"});
            statement.setString(1, message.getText());
            statement.setLong(2, message.getUser().getId());
            return statement;
        }, keyHolder);

        message.setId((Long) keyHolder.getKey());
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
