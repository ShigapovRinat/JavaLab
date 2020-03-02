package ru.javalab.multichatWithJdbcTemplate.repositories;

import ru.javalab.multichatWithJdbcTemplate.models.Message;

public interface MessagesRepository extends CrudRepository<Long, Message> {
}
