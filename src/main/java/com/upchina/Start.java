package com.upchina;

import org.apache.activemq.artemis.api.core.client.*;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
/**
 * Created by anjunli on  2021/9/8
 **/
public class Start {
    public static void main(String[] args) throws Exception {
        EmbeddedActiveMQ embedded  = new EmbeddedActiveMQ();
        embedded.start();

        ServerLocator serverLocator =  ActiveMQClient.createServerLocator("vm://0");

        ClientSessionFactory factory =  serverLocator.createSessionFactory();

        ClientSession session = factory.createSession();

        session.createQueue("example", "example", true);

        ClientProducer producer = session.createProducer("example");

        ClientMessage message = session.createMessage(true);

//        message.getBody().writeString("Hello");

        producer.send(message);

        session.start();

        ClientConsumer consumer = session.createConsumer("example");

        ClientMessage msgReceived = consumer.receive();

//        System.out.println("message = " + msgReceived.getBody().readString());

        session.close();
    }
}
