package consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import models.UserData;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static consumer.CreatorDocument.createPdf;

public abstract class Consumer {

    private final static String EXCHANGE_NAME = "documents";
    private final static String EXCHANGE_TYPE = "fanout";
    private final static String NAME_DIRECTORY = "documents/";

    public void consume() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            String queue = channel.queueDeclare().getQueue();

            channel.queueBind(queue, EXCHANGE_NAME, "");


            DeliverCallback deliverCallback = (consumerTag, message) -> {
                try (ByteArrayInputStream b = new ByteArrayInputStream(message.getBody());
                     ObjectInputStream o = new ObjectInputStream(b)) {
                    UserData userData = (UserData) o.readObject();
                    File file = new File(NAME_DIRECTORY + getPackageName() + userData.getSurname() + "_" + UUID.randomUUID().toString() + ".pdf");
                    createPdf(file.getPath(), getText(userData));
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract String getPackageName();

    public abstract String getText(UserData userData);
}
