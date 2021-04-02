package thread;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/3/31 10:11
 */
public class ThreadTest {


    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Thread t = Thread.currentThread();
            System.out.println("当前线程："+t.getName());
        };
        System.out.println("主线程");
        //启动一个新线程
        Thread thread = new Thread(task);
        thread.setName("Test-Thread-1");
        //主线程执行完发现新线程或剩余线程为守护线程setDaemon(true)，会终止当前JVM执行
        //thread.setDaemon(true);
        thread.start();

        Thread t2 = new Thread(task);
        t2.setName("Test-Thread-2");
        t2.start();
        t2.join();
    }
}
