package getvaluetest;

import homework.CallableGetThreadValue;
import homework.CountDownThreadValue;
import homework.CyclicBarrierThreadValue;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/9 15:30
 */
public class GetThreadValueTest {
    /**
     * 使用Callable接口获取线程返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void callableTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        Callable<Integer> task = new CallableGetThreadValue();
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        Future<Integer> future = executorService.submit(task);
        //这是得到的返回值
        int result = future.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    /**
     * 使用countDownLatch的方式异步获取线程返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void countDownTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        Callable<Integer> task = new CountDownThreadValue(countDownLatch);
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        Future<Integer> future = executorService.submit(task);
        //这是得到的返回值
        countDownLatch.await(1,TimeUnit.SECONDS);
        int result = future.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    /**
     * 使用CyclicBarrier获取线程返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void cyclicBarrierTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        int num = 1;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num);
        Callable<Integer> task = new CyclicBarrierThreadValue(cyclicBarrier);
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        Future<Integer> future = executorService.submit(task);
        //这是得到的返回值
        int result = future.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    /**
     * 使用FutureTask获取线程返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void futureTaskTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        FutureTask<Integer> task = new FutureTask<>(new CallableGetThreadValue());
        // 在这里创建一个线程或线程池
        new Thread(task).start();
        int result = task.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }


    /**
     * 使用CompletableFuture获取线程返回结果
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> fibo(36),Executors.newCachedThreadPool());
        int result = task.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

}
