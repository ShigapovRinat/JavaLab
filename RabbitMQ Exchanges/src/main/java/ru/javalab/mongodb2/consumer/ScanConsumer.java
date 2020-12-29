package ru.javalab.mongodb2.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import ru.javalab.mongodb2.model.Scan;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ScanConsumer extends BaseConsumer {
    public ScanConsumer() {
        super("doc", "direct", "doc.scan");

    }

    @Override
    protected DeliverCallback getDeliveryCallback() {
        return (consumerTag, message) -> {
            try {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Scan scan = objectMapper.readValue(message.getBody(), Scan.class);
                    String path = "C:/Users/pc/JavaLab/RabbiMQ Exchanges/scan" + UUID.randomUUID() + ".jpg";
                    FileOutputStream fileOutputStream = new FileOutputStream(path);
                    fileOutputStream.write(scan.getBytes());
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                getChannel().basicAck(message.getEnvelope().getDeliveryTag(), false);
            } catch (JsonProcessingException e) {
                getChannel().basicReject(message.getEnvelope().getDeliveryTag(), false);
            }
        };
    }
}