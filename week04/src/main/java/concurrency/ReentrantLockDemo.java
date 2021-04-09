package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/6 10:53
 */
public class ReentrantLockDemo {

    private  static int sum = 0 ;
    //可重入 非公平锁
    private Lock lock = new ReentrantLock(true);

    public int addAndGet(){
        try {
            //lock.lock();
            return ++sum;
        }finally {
            //lock.unlock();
        }
    }

    public int getSum(){
        return sum;
    }

    public static void main(String[] args) {
        int loopNum = 1000;
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        //启用parallel并行Stream
        IntStream.range(0, loopNum).parallel().forEach(i -> reentrantLockDemo.addAndGet());
        System.out.println(reentrantLockDemo.getSum());

    }
}
