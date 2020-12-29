package ru.javalab.mongodb2;

import ru.javalab.mongodb2.consumer.DocumentConsumer;
import ru.javalab.mongodb2.consumer.ScanConsumer;
import ru.javalab.mongodb2.consumer.VacationDocumentConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        new VacationDocumentConsumer().start();
        new DocumentConsumer().start();
        new ScanConsumer().start();
    }
}