package ru.javalab.jlmq.server.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.javalab.jlmq.server.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}