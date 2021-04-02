package nio.gateway.inbound;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import nio.gateway.filter.HeaderHttpRequestFilter;
import nio.gateway.filter.HttpRequestFilter;
import nio.gateway.filter.UrlHttpRequestFilter;
import nio.gateway.outbound.httpclient4.HttpOutboundHandler;
import nio.gateway.outbound.okhttp.OkhttpOutboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author JasonLin
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final List<String> proxyServer;
    private OkhttpOutboundHandler handler;
    private HttpRequestFilter headerRequestFilter = new HeaderHttpRequestFilter();
    private HttpRequestFilter urlRequestFilter = new UrlHttpRequestFilter();

    public HttpInboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
        //更换成使用OkHttp
        this.handler = new OkhttpOutboundHandler(this.proxyServer);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            System.out.println("接收到的请求url为:"+uri);
            //url拦截
            urlRequestFilter.filter(fullRequest,ctx);
            //接收客户端URL进行操作
            handler.handle(fullRequest, ctx, headerRequestFilter);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
