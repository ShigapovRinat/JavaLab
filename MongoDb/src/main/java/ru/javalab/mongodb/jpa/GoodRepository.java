package ru.javalab.mongodb.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodRepository extends MongoRepository<Good, String> {

    @Query(value = "{producer : ?0, $or: [{cpu : {$exists: ?2}}, {'title' : ?1}]}")
    List<Good> find(@Param("producer") String producer, @Param("title") String title, @Param("exists") boolean isExist);
}