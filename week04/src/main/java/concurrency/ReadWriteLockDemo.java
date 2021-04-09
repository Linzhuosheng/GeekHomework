package concurrency;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/6 16:27
 */
public class ReadWriteLockDemo {

    private int sum = 0;
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public int addAndGet() {
        try {
            readWriteLock.writeLock().lock();
            // ++ 操作本身线程不安全
            return ++sum;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public int getSum() {
        try {
            readWriteLock.readLock().lock();
            return sum;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {

        int loopNum = 1000;
        ReadWriteLockDemo readWriteLock = new ReadWriteLockDemo();
        //启用parallel并行Stream
        IntStream.range(0, loopNum).parallel().forEach(i -> {
            readWriteLock.addAndGet();
            // System.out.println("读取当前值:"+readWriteLock.getSum());
        });
        System.out.println(readWriteLock.getSum());

    }

}
