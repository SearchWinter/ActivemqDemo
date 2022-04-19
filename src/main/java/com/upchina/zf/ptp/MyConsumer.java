package com.upchina.zf.ptp;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by anjunli on  2021/9/7
 * https://zhuanlan.zhihu.com/p/83264194
 * PTP——点对点
 * 1：点对点消息传递域的特点如下
 * （1）每个消息只能有一个消费者
 * （2）消息的生产者和消费者没有时间上的相关性。无论消费者在生产者发送消息的时候是否处于运行状态，它都可以提取消息。
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
            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "nio://192.168.64.131:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("q_test");
            messageConsumer = session.createConsumer(destination);

            //一次只能消费一条数据
/*            while(true) {
                message = messageConsumer.receive();
                String msg = ((TextMessage) message).getText();
                System.out.println("receive-> " + msg);
            }*/
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
