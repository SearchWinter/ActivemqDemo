package com.upchina.Broker;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.store.kahadb.KahaDBStore;

import java.io.File;

/**
 * Created by anjunli on  2021/9/8
 * 嵌入式Broker
 * https://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection
 *
 **/
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        //第一种：BrokerService启动Broker
        BrokerService brokerService = new BrokerService();

        /** 使用KahaDB实现消息存储持久化*/
        File file = new File("data/");
        KahaDBStore kahaDBStore = new KahaDBStore();
        //KahaDB存放的路径，默认值是activemq-data
        kahaDBStore.setDirectory(file);
        //设置每个消息data log的大小，默认是32MB 1024 * 1024 * 32
        kahaDBStore.setJournalMaxFileLength(1024*1024*1);
        //批量写入磁盘索引page数量，默认值1000
        kahaDBStore.setIndexWriteBatchSize(100);
        //是否支持异步索引，默认false
        kahaDBStore.setEnableIndexWriteAsync(true);
        //设置是否保证每个没有事务的内容，被同步写入磁盘，JMS持久化的时候需要，默认为true
        kahaDBStore.setEnableJournalDiskSyncs(true);

        brokerService.setPersistenceAdapter(kahaDBStore);
        brokerService.setBrokerName("MyActiveMQ");
        //是否启用JMS监控功能
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
        //正常启动后，会很快关闭
//        System.in.read();
    }
}
