package week02;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/3/23 15:46
 */
public class HttpService {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8801);
            while (true) {
                Socket socket = serverSocket.accept();
                service(socket);
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
            String body = "Hello NIO Client 1 !";
            pw.println("Content-Length" + body.length());
            pw.println();
            pw.write(body);
            pw.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
