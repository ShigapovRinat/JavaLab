package ru.javalab.messagequeue.stomp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javalab.messagequeue.stomp.protocol.Jlmq;
import ru.javalab.messagequeue.stomp.protocol.JlmqConnector;
import ru.javalab.messagequeue.stomp.protocol.JlmqConsumer;

@Controller
public class ConsumerController {

    @Autowired
    StompSessionHandlerAdapter customerStompSessionHandler;

    @GetMapping("/consumer")
    public ModelAndView getConsumerPage() {
        return new ModelAndView("consumer");
    }

    @PostMapping("/consumer")
    public ModelAndView createConsumer(@RequestParam("queueName") String queueName) {
        JlmqConnector connector = Jlmq.connector()
                .address("ws://localhost:8080/messages")
                .connect();
        JlmqConsumer consumer = connector.consumer()
                .subscribe("queue/" + queueName)
                .onReceive(customerStompSessionHandler)
                .create();
        return new ModelAndView("redirect:/consumer");
    }
}