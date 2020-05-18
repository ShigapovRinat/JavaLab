package ru.javalab.messagequeue.stomp.protocol;

import lombok.AllArgsConstructor;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@AllArgsConstructor
public class JlmqConnector {
    private static String address;
    private static WebSocketClient webSocketClient;

    public JlmqConnector(String address, WebSocketClient webSocketClient) {
        JlmqConnector.address = address;
        JlmqConnector.webSocketClient = webSocketClient;
    }

    public JlmqProducerBuilder producer() {
        return new JlmqProducerBuilder();
    }

    public JlmqConsumerBuilder consumer() {
        return new JlmqConsumerBuilder();
    }


    public static class JlmqProducerBuilder {
        private String queueName;
        private StompSessionHandler sessionHandler;

        public JlmqProducerBuilder toQueue(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public JlmqProducerBuilder onReceive(StompSessionHandler sessionHandler) {
            this.sessionHandler = sessionHandler;
            return this;
        }

        public JlmqProducer create() {
            try {
                WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
                stompClient.setMessageConverter(new MappingJackson2MessageConverter());
                StompSession session = stompClient.connect(address, sessionHandler).get();
                return new JlmqProducer(queueName, session);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println();
                throw new IllegalArgumentException(e);
            }
        }
    }

    public static class JlmqConsumerBuilder {
        private String queueName;
        private StompSessionHandler sessionHandler;

        public JlmqConsumerBuilder subscribe(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public JlmqConsumerBuilder onReceive(StompSessionHandler sessionHandler) {
            this.sessionHandler = sessionHandler;
            return this;
        }

        public JlmqConsumer create() {
            try {
                WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
                stompClient.setMessageConverter(new StringMessageConverter());
                StompSession session = stompClient.connect(address, sessionHandler).get();
                System.out.println("/jlmq/" + queueName);
                session.subscribe("/jlmq/" + queueName, sessionHandler);
                return new JlmqConsumer(session);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println();
                throw new IllegalArgumentException(e);
            }
        }
    }


}