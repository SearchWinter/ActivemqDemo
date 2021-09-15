package com.upchina.zf;

/**
 * Created by anjunli on  2021/9/7
 **/
public class TestProducer {
    public static void main(String[] args) {
        MyProducer myProducer = new MyProducer();
        myProducer.sendToActiveMQ();

    }
}
