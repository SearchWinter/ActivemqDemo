package com.upchina.Broker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;

/**
 * Created by anjunli on  2021/9/16
 * https://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection
 **/
public class EmbedBroker2 {
    public static void main(String[] args) throws Exception {
        //第二种：使用BrokerFactory创建Broker
        //都在activemq.xml里面设置
        BrokerService broker = BrokerFactory.createBroker("xbean:activemq.xml");
        broker.start();

        System.in.read();
    }
}
