package ru.javalab.messagequeueclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavalabMessageQueueClientApplication {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(JavalabMessageQueueClientApplication.class, args);

        JavalabMessageQueueConsumer consumer;
        consumer = new JavalabMessageQueueConsumer()
                .onFail(System.out::println)
                .onSuccess(System.out::println)
                .handledClass(String.class)
                .connect();

        consumer.create("queue");
        for (int i = 0; i < 10; i++) {
            consumer.add(i);
        }
        consumer.subscribe("queue");

    }


}
