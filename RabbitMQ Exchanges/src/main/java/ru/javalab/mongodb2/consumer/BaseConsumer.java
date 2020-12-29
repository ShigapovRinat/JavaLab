package ru.javalab.mongodb2.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.Getter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class BaseConsumer {
    @Getter
    private Channel channel;
    private String exchangeName;
    private String exchangeType;
    private String routingKey;

    public BaseConsumer(String exchangeName, String exchangeType, String routingKey) {
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
        this.routingKey = routingKey;
    }

    public void start() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.basicQos(3);

        channel.exchangeDeclare(exchangeName, exchangeType);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, exchangeName, routingKey);
        channel.basicConsume(queue, false, getDeliveryCallback(), consumerTag -> {
        });
    }

    protected abstract DeliverCallback getDeliveryCallback();
}