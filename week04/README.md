# Week 4作业

### 作业2：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，异步运行一个方法，拿到这个方法的返回值后，退出主线程？写出你的方法，越多越好，提交到github。

目前实现方式有5种，具体源码在test.getvaluetest包中。

## Callable方式

```java
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
```

## CountDownLatch方式

```java
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
        countDownLatch.await(1, TimeUnit.SECONDS);
        int result = future.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
```

## CyclicBarrier方式
```java
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
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
```

## FutureTask 方式

```java
 @Test
    public void futureTaskTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        FutureTask<Integer> task = new FutureTask<>(new CallableGetThreadValue());
        // 在这里创建一个线程或线程池
        new Thread(task).start();
        int result = task.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
```

## CompletableFuture方式

```java
@Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> fibo(36), Executors.newCachedThreadPool());
        int result = task.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
```

