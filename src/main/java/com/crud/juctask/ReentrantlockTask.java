package com.crud.juctask;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantlockTask {
    public static void main(String[] args) {
        char[] ai = "123456".toCharArray();
        char[] ac = "abcdef".toCharArray();

        Lock lock = new ReentrantLock();
        Condition ct1 = lock.newCondition();
        Condition ct2 = lock.newCondition();
//dsadadsadasdasda
        new Thread(() -> {
            lock.lock();
            for(char c:ai){
                System.out.println(c);
                try {
                    System.out.println(c);
                    ct2.signal();
                    ct1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            for (char c:ac){
                try {
                    ct2.await();
                    System.out.println(c);
                    ct1.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }
        }).start();
    }


}
