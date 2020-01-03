package com.crud.juctask;

import java.util.concurrent.CountDownLatch;

public class WaitNotifyTask {
    private volatile static boolean isII = false;
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        final Object o = new Object();
        char[] ai = "123456".toCharArray();
        char[] ac = "abcdef".toCharArray();

        new Thread(() -> {
//            while (!isII){
//                try {
//                    o.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o){
                for(char c:ai){
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }).start();

        new Thread(() ->{
            //isII = true;
            synchronized (o){
                for(char c:ac){
                    System.out.println(c);
                    latch.countDown();
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }).start();
    }
}
