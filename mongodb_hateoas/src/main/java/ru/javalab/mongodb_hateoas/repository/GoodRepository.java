package ru.javalab.mongodb_hateoas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.javalab.mongodb_hateoas.model.Good;

import java.util.List;

public interface GoodRepository extends MongoRepository<Good, String> {

    @RestResource(path = "producer", rel = "producer")
    @Query(value = "{producer : ?0, $or: [{cpu : {$exists: ?2}}, {'title' : ?1}]}")
    List<Good> find(@Param("producer") String producer, @Param("title") String title, @Param("exists") boolean isExist);
}