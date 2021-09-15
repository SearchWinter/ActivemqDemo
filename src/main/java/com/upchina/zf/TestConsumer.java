package com.upchina.zf;

/**
 * Created by anjunli on  2021/9/7
 **/
public class TestConsumer {
    public static void main(String[] args) {
        MyConsumer myConsumer = new MyConsumer();
        myConsumer.receiveFromMQ();
    }
}
