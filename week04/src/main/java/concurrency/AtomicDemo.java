package concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/6 16:45
 */
public class AtomicDemo {

    private AtomicInteger sum = new AtomicInteger(0);

    private int addAndGet() {
        return sum.incrementAndGet();
    }

    private int getSum() {
        return sum.get();
    }

    public static void main(String[] args) {

        int loopNum = 1000;
        AtomicDemo atomicDemo = new AtomicDemo();
        //启用parallel并行Stream
        IntStream.range(0, loopNum).parallel().forEach(i -> {
            atomicDemo.addAndGet();
            // System.out.println("读取当前值:"+readWriteLock.getSum());
        });
        System.out.println(atomicDemo.getSum());
    }
}
