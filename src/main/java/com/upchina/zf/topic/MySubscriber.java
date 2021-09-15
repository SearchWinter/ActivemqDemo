package com.upchina.zf.topic;

import com.upchina.zf.ptp.MyListener;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by anjunli on  2021/9/8
 *
 * 持久的Topic消息示例
 *    1、给连接设置唯一的ID,创建DurableTopicSubscriber来订阅
 *    2、设置完成后再start() Connection
 *    3、消费者要先运行一次，相当于向服务注册这个消费者，然后运行生产者。这个时候无论消费者是否在线，都会接收到，下次连接的时候，会把没有收过的消息都接收下来。
 *
 *  注意：对于非持久性的Topic消息，则需要接收者保持运行状态，不然消息发送者发出的消息会接收不到。
 **/
public class MySubscriber implements Runnable {

    public String ID;

    public MySubscriber() {
    }

    public MySubscriber(String ID) {
        this.ID = ID;
    }

    TopicConnection topicConnection;
    TopicSession topicSession;
    TopicSubscriber topicSubscriber;

    @Override
    public void run() {
        try {
            TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://172.16.11.161:61616");
            topicConnection = connectionFactory.createTopicConnection();
            topicConnection.setClientID("Client_"+ID);
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //创建队列消息，topic
            Topic topic = topicSession.createTopic("topic_test");

            //创建消息订阅者
//            topicSubscriber = topicSession.createSubscriber(topic);
            /** 创建持久的TopicSubscriber*/
            topicSubscriber = topicSession.createDurableSubscriber(topic, "Subscriber_"+ID);
            topicConnection.start();

            //接受消息，会阻塞线程
/*            Message message = topicSubscriber.receive();
            //输出消息
            String msg = ((TextMessage) message).getText();
            System.out.println(msg);*/

            topicSubscriber.setMessageListener(new MyListener());

            System.in.read();
        } catch (IOException | JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                topicSubscriber.close();
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                try {
                    topicSession.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        topicConnection.close();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
