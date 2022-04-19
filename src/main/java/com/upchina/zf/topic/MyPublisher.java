package com.upchina.zf.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by anjunli on  2021/9/8
 **/
public class MyPublisher {
    TopicConnection topicConnection;
    TopicSession topicSession;
    TopicPublisher topicPublisher;

    public void publishTopic(){
        try {
            TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.64.131:61616");
            topicConnection = connectionFactory.createTopicConnection();
            topicConnection.start();
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = topicSession.createTopic("topic_test");
            topicPublisher = topicSession.createPublisher(topic);

            long start = System.currentTimeMillis();
            for (int i = 0; i <1000 ; i++) {
                topicPublisher.send(topicSession.createTextMessage("topic message test"+i));
            }
            System.out.println(System.currentTimeMillis()-start);
        }catch(JMSException e){
            e.printStackTrace();
        }finally{
            try {
                topicPublisher.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }finally{
                try {
                    topicSession.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }finally{
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
