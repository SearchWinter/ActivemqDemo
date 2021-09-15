package com.upchina.Broker;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;

/**
 * Created by anjunli on  2021/9/8
 * 嵌入式Broker
 * https://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection
 **/
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setBrokerName("MyActiveMQ");
//        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://127.0.0.1:60000");
        brokerService.start();
    }
}
