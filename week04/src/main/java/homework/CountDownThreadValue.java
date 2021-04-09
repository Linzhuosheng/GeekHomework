package homework;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/9 16:14
 */
public class CountDownThreadValue implements Callable<Integer> {

    private CountDownLatch countDownLatch;

    public CountDownThreadValue(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
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
        this.countDownLatch.countDown();
        return result;
    }

}
