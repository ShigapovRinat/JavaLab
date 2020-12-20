package ru.javalab.mongodb.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CpuRepository extends MongoRepository<Cpu, String> {

}