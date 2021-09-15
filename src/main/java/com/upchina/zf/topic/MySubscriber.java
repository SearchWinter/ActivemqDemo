package com.upchina.zf.topic;

import com.upchina.zf.MyListener;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by anjunli on  2021/9/8
 **/
public class MySubscriber implements Runnable {

    TopicConnection topicConnection;
    TopicSession topicSession;
    TopicSubscriber topicSubscriber;

    @Override
    public void run() {
        try {
            TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://172.16.11.161:61616");
            topicConnection = connectionFactory.createTopicConnection();
            topicConnection.start();
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建队列消息，topic
            Topic topic = topicSession.createTopic("topic_test");
            //创建消息订阅者
            topicSubscriber = topicSession.createSubscriber(topic);

            //接受消息，会阻塞线程
            Message message = topicSubscriber.receive();
            //输出消息
            String msg = ((TextMessage) message).getText();
            System.out.println(msg);

//            topicSubscriber.setMessageListener(new MyListener());
        } catch (JMSException  e) {
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
