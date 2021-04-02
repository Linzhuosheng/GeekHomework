package backendservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池版本
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/3/23 16:11
 */
public class HttpService03 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);
        try {
            final ServerSocket serverSocket = new ServerSocket(8873);
            while (true) {
                final Socket socket = serverSocket.accept();
                executorService.execute(() -> service(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void service(Socket socket) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type:text/html;charset = utf-8");
            String body = "This is Backend Service 03 !";
            pw.println("Content-Length:" + body.getBytes().length);
            pw.println();
            pw.write(body);
            pw.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
