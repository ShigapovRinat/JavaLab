package ru.javalab.messagequeue.stomp.protocol;

import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

public class Jlmq {

    public static Connector connector() {
        return new Connector();
    }

    public static class Connector {
        private String address;

        public Connector address(String address) {
            this.address = address;
            return this;
        }

        public JlmqConnector connect() {
            List<Transport> transports = new ArrayList<>(1);
            transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
            return new JlmqConnector(address, new SockJsClient(transports));
        }
    }
}