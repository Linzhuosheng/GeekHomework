package homework;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/9 16:14
 */
public class CyclicBarrierThreadValue implements Callable<Integer> {

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierThreadValue(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier = cyclicBarrier;
    }

    public static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    @Override
    public Integer call() throws Exception {
        int result = fibo(36);
        this.cyclicBarrier.await(1, TimeUnit.SECONDS);
        return result;
    }

}
