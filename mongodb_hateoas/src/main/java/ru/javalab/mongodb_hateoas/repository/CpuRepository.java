package ru.javalab.mongodb_hateoas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.javalab.mongodb_hateoas.model.Cpu;

public interface CpuRepository extends MongoRepository<Cpu, String> {

}