package ru.javalab.mongodb.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        MongoTemplate template = context.getBean(MongoTemplate.class);


        List<Good> goods = template.find(new Query(
                where("cpu").exists(true)
                        .orOperator(where("title").is("phone")
                        ,where("price").is("7000"))), Good.class, "good");
        System.out.println(goods);


    }
}