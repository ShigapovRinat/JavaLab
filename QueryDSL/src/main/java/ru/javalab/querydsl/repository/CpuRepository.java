package ru.javalab.querydsl.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.javalab.querydsl.model.Cpu;

public interface CpuRepository extends MongoRepository<Cpu, String>, QuerydslPredicateExecutor<Cpu> {

}