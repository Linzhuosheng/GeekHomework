package week02.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/3/26 15:36
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)  {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try{
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String url = fullHttpRequest.uri();
            if(url.contains("/test")){
                handlerTest(fullHttpRequest,ctx,"Hello Jason");
            }else {
                handlerTest(fullHttpRequest,ctx,"Hello Others");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }

    }

    private void handlerTest(FullHttpRequest fullHttpRequest,ChannelHandlerContext ctx ,String content){
        FullHttpResponse response = null;
        try {
            String value = content;
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
            response.headers().set("Content-Type","application/json");
            response.headers().setInt("Content-Length",response.content().readableBytes());
        }catch (Exception e){
            System.out.println("处理异常"+e.getMessage());
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.NO_CONTENT);
        }finally {
            if(fullHttpRequest != null){
                if(!HttpUtil.isKeepAlive(fullHttpRequest)){
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                }else {
                    response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
                    ctx.write(response);
                }
            }
        }

    }
}
