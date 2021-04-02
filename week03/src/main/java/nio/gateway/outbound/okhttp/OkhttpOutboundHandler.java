package nio.gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import nio.gateway.filter.HeaderHttpResponseFilter;
import nio.gateway.filter.HttpRequestFilter;
import nio.gateway.filter.HttpResponseFilter;
import nio.gateway.outbound.httpclient4.NamedThreadFactory;
import nio.gateway.router.HttpEndpointRouter;
import nio.gateway.router.RandomHttpEndpointRouter;

import nio.gateway.router.RoundRibbonRouter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 使用Okhttp处理后端请求
 * （必做）整合你上次作业的 httpclient/okhttp
 * @author JasonLin
 */
public class OkhttpOutboundHandler {
    private CloseableHttpAsyncClient httpclient;
    private ExecutorService proxyService;
    private List<String> backendUrls;

    HttpResponseFilter headerResponseFilter = new HeaderHttpResponseFilter();
    //随机路由
    //HttpEndpointRouter router = new RandomHttpEndpointRouter();
    //轮询路由
    HttpEndpointRouter router =new RoundRibbonRouter();


    public OkhttpOutboundHandler(List<String> backends) {

        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());

        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        //拒绝策略
        //RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores * 2,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);

        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();

        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();
        httpclient.start();
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/") ? backend.substring(0, backend.length() - 1) : backend;
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        //请求过滤
        filter.filter(fullRequest, ctx);
        proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        //TODO 修改为使用Okhttp访问
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                //默认使用Get请求，可以不写
                .header(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE)
                .get()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            handleResponse(inbound, ctx, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response endpointResponse) {
        FullHttpResponse response = null;
        try {

            byte[] body = endpointResponse.body().bytes();
            System.out.println("获取返回体:" + new String(body));
            System.out.println("返回体长度:" + body.length);

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(Objects.requireNonNull(endpointResponse.headers().get("Content-Length"))));

            headerResponseFilter.filter(response);

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
