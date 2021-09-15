package com.upchina.zf.topic;

import com.upchina.zf.MyProducer;

/**
 * Created by anjunli on  2021/9/8
 **/
public class TestSubscriber {
    public static void main(String[] args) {
        MySubscriber mySubscriber = new MySubscriber();
        Thread subscriber01 = new Thread(mySubscriber, "subscriber01");
        Thread subscriber02 = new Thread(mySubscriber, "subscriber02");

        subscriber01.start();
        subscriber02.start();
    }
}
