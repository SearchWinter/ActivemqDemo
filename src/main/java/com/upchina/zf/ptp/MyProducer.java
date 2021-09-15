package com.upchina.zf.ptp;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
Created by anjunli on  2021/9/7
创建消息生成者，发送消息
创建会话，参数含义:
1.transacted - 是否使用事务
2.acknowledgeMode - 消息确认机制，可选机制为：
 1）Session.AUTO_ACKNOWLEDGE - 自动确认消息
 2）Session.CLIENT_ACKNOWLEDGE - 客户端确认消息机制
 3）Session.DUPS_OK_ACKNOWLEDGE - 有副本的客户端确认消息机制
 **/
public class MyProducer {
    //定义连接工厂
    ConnectionFactory connectionFactory;
    //定义连接
    Connection connection;
    //定义会话
    Session session;
    //定义消息目的地
    Destination destination;
    //定义消息生产者
    MessageProducer mProducer;
    //定义消息
    Message message;

    public void sendToActiveMQ()  {
        try {
            //用户名 密码 访问ActiveMQ服务的路径
            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
            //创建目的地，也就是队列名
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话
            session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建目的地，也就是队列名
            destination = session.createQueue("q_test");
            //创建消息生成者，该生成者与目的地绑定
            mProducer = session.createProducer(destination);

            //创建消息
//            message = session.createTextMessage("ActiveMQ test");
            //发送消息
//            mProducer.send(message);
            for (int i = 0; i <10 ; i++) {
                mProducer.send(session.createTextMessage("ActiveMQ test"+i));
                Thread.sleep(100L);
            }

        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (mProducer != null) {
                    mProducer.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (session != null) {
                        session.close();
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
