package ru.javalab.chatWebSocket.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.javalab.chatWebSocket.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
