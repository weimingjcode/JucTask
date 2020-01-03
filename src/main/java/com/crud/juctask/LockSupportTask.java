package com.crud.juctask;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTask {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        char[] a1 = "1234567".toCharArray();
        char[] a2 = "abcdefg".toCharArray();
        t1 = new Thread(() -> {
            for (char c : a1){
                System.out.println(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(() -> {
            for (char c : a2) {
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(t1);
            }
        });
        t1.start();
        t2.start();
    }
}
