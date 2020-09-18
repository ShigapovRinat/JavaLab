package producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import models.UserData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ProducerReader {

    private final static String EXCHANGE_NAME = "documents";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserData user = new UserData();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            try (ByteArrayOutputStream b = new ByteArrayOutputStream();
                 ObjectOutputStream o = new ObjectOutputStream(b)) {

                while (true) {
                    System.out.println("Ваше имя");
                    user.setName(sc.next());
                    System.out.println("Ваша фамилия");
                    user.setSurname(sc.next());
                    System.out.println("Номер и серия паспорт");
                    user.setPassport(sc.nextInt());
                    System.out.println("ИНН");
                    user.setInn(sc.nextInt());
                    System.out.println("Ваш возраст");
                    user.setAge(sc.nextInt());
                    System.out.println("Дата выдачи");
                    user.setDate(sc.next());
                    System.out.println(user.toString());
                    System.out.println("Хотите продолжить? Напишите \"нет\" если хотите закончить.");


                    o.writeObject(user);
                    channel.basicPublish(EXCHANGE_NAME, "", null, b.toByteArray());

                    if (sc.next().equals("нет")) {
                        break;
                    }
                }
        }
    } catch(IOException |
    TimeoutException e)

    {
        throw new IllegalArgumentException(e);
    }
}
}
