package ru.javalab.mongodb2.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import ru.javalab.mongodb2.model.User;

public class DocumentConsumer extends BaseConsumer {

    public DocumentConsumer() {
        super("exchange", "topic", "doc.#");
    }

    @Override
    protected DeliverCallback getDeliveryCallback() {
        return (consumerTag, message) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(message.getBody(), User.class);
            System.out.println(user.toString());
            getChannel().basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
    }
}