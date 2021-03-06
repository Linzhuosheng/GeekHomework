package backendservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * IO多线程版本
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/3/23 16:02
 */
public class HttpService02 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8802);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(()->{
                    service(socket);
                }).start();
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
            String body = "This is Backend Service 02 !";
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
