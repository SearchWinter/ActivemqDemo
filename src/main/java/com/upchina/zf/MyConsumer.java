package com.upchina.zf;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by anjunli on  2021/9/7
 * PTP——点对点
 **/
public class MyConsumer {
    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    Destination destination;
    MessageConsumer messageConsumer;
    Message message;

    public void receiveFromMQ(){
        try {
            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://172.16.11.161:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("q_test");
            messageConsumer = session.createConsumer(destination);

/*            message = messageConsumer.receive();
            String msg = ((TextMessage) message).getText();
            System.out.println("receive-> " + msg);*/

            //加载监听器
            messageConsumer.setMessageListener(new MyListener());
            //监听器需要持续加载，使用输入流阻塞当前线程结束
            System.in.read();
        }catch (JMSException | IOException e){
            e.printStackTrace();
        }finally{
            try {
                messageConsumer.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            try {
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
