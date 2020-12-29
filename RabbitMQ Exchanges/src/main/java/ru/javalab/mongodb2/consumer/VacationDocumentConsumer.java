package ru.javalab.mongodb2.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import ru.javalab.mongodb2.creator.VacationDocumentCreator;
import ru.javalab.mongodb2.model.User;


public class VacationDocumentConsumer extends BaseConsumer {
    public VacationDocumentConsumer() {
        super("vocation", "fanout", "");
    }

    @Override
    protected DeliverCallback getDeliveryCallback() {
        return (consumerTag, message) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(message.getBody(), User.class);
            new VacationDocumentCreator(user).createDocument();
            getChannel().basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
    }
}