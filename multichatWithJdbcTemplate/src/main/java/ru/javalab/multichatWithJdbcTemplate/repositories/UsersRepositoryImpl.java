package ru.javalab.multichatWithJdbcTemplate.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.javalab.multichatWithJdbcTemplate.models.Message;
import ru.javalab.multichatWithJdbcTemplate.models.User;

import java.sql.PreparedStatement;
import java.util.*;

public class UsersRepositoryImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_INSERT = "insert into \"user\"(id, login, password) values (?, ?, ?)";
    //language=SQL
    private static final String SQL_DELETE = "delete from \"user\" where id = ?";
    //language=SQL
    private static final String SQL_SELECT_BY_ID= "select \"user\".id as user_id, \"user\".login ,\n" +
            "\"user\".password, m.id_message, m.text\n" +
            "from \"user\" left join message m on \"user\".id = m.id_user where \"user\".id = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select \"user\".id as user_id, \"user\".login,\n" +
            "\"user\".password, m.id_message, m.text\n" +
            "from \"user\" left join message m on \"user\".id = m.id_user";

    private JdbcTemplate jdbcTemplate;
    private Map<Long, User> usersMap = new HashMap<>();

    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<User> userRowMapper = (row, rowNumber) -> {
        Long id = row.getLong("user_id");

        if (!usersMap.containsKey(id)) {
            User user = User.builder()
                    .id(id)
                    .login(row.getString("login"))
                    .password(row.getString("password"))
                    .messages(new ArrayList<>())
                    .build();
            usersMap.put(id, user);
        }

        Message message = Message.builder()
                .id(row.getLong("id_message"))
                .text(row.getString("text"))
                .user(usersMap.get(row.getLong("user_id")))
                .build();

        usersMap.get(id).getMessages().add(message);
        return usersMap.get(id);
    };


    @Override
    public Optional<User> find(Long id) {
        jdbcTemplate.query(SQL_SELECT_BY_ID, userRowMapper, id);

        if (usersMap.containsKey(id)) {
            return Optional.of(usersMap.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT, new String[]{"id"});
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            return statement;
        }, keyHolder);

        user.setId((Long) keyHolder.getKey());
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
