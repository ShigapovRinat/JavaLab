package ru.javalab.chat.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.javalab.chat.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
