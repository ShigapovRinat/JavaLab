package ru.javalab.hateoas.reposiories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.javalab.hateoas.models.Message;

import java.util.List;

//@RepositoryRestResource
public interface MessagesRepository extends CrudRepository<Message, Long> {
//    List<Message> findAll();

}
