package ru.javalab.querydsl.repository;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import ru.javalab.querydsl.model.Good;
import ru.javalab.querydsl.model.QGood;

public interface GoodRepository extends MongoRepository<Good, String>, QuerydslPredicateExecutor<Good>, QuerydslBinderCustomizer<QGood> {

    @Override
    default void customize(QuerydslBindings bindings, QGood qGood) {
        bindings.bind(qGood.cpu().title).as("good.cpu.name").first(
                StringExpression::containsIgnoreCase
        );
    }

}

