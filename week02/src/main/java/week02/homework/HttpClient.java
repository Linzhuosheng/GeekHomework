package week02.homework;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * （必做）写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/3/26 16:16
 */
public class HttpClient {

    private static final String URL = "http://localhost:8801";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //默认使用Get请求，可以不写
                .get()
                .url(URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("返回结果:"+response.body().string());
        } catch (IOException e) {
            System.out.println("Request Error!");
            e.printStackTrace();
        }

    }
}
