package nio.gateway.filter;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 请求URL拦截器
 * （必做）实现过滤器。
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/2 10:44
 */
public class UrlHttpRequestFilter implements HttpRequestFilter{
    private static final String[] ILLEGAL_URLS = {"/haha", "/jason"};

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
            for(String url : ILLEGAL_URLS){
                if(fullRequest.uri().contains(url)){
                    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,FORBIDDEN);
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                    ctx.flush();
                    ctx.close();
                }
            }
    }
}
