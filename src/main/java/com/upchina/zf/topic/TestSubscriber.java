package com.upchina.zf.topic;

/**
 * Created by anjunli on  2021/9/8
 **/
public class TestSubscriber {
    public static void main(String[] args) {
        MySubscriber mySubscriber = new MySubscriber("101");
        MySubscriber mySubscriber2 = new MySubscriber("102");
        //利用线程来模拟多个消费者的情况
        Thread subscriber01 = new Thread(mySubscriber, "subscriber01");
        Thread subscriber02 = new Thread(mySubscriber2, "subscriber02");

        subscriber01.start();
        subscriber02.start();
    }
}
