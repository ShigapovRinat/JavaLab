package ru.javalab.chatWebSocket.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.javalab.chatWebSocket.models.Chat;
import ru.javalab.chatWebSocket.models.User;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
    List<Chat> findByCreator(User user);
}
