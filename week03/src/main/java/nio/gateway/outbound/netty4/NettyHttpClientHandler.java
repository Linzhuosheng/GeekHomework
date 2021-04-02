package nio.gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


public class NettyHttpClientHandler extends ChannelInboundHandlerAdapter {

    /*@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }*/
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {
            FullHttpResponse response = (FullHttpResponse) msg;

            ByteBuf content = response.content();
            HttpHeaders headers = response.headers();

            System.out.println("content:" + System.getProperty("line.separator") + content.toString(CharsetUtil.UTF_8));
            System.out.println("headers:" + System.getProperty("line.separator") + headers.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}