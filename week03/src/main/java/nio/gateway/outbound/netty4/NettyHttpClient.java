package nio.gateway.outbound.netty4;//package io.github.kimmking.gateway.outbound;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.apache.http.protocol.HTTP;

import java.net.URI;

/**
 * （选做）使用 netty 实现后端 http 访问
 * @author JasonLin
 */
public class NettyHttpClient {

    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    //客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new NettyHttpClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            URI url = new URI("/");
            //配置HttpRequest的请求数据和一些配置信息
            FullHttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_0, HttpMethod.GET, url.toASCIIString());

            request.headers()
                    .set(HttpHeaderNames.HOST, host)
                    .set(HttpHeaderNames.CONTENT_TYPE, "Content-Type:text/html;charset = utf-8")
                    .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                    //设置传递请求内容的长度
                    .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        NettyHttpClient client = new NettyHttpClient();
        client.connect("127.0.0.1", 8888);
    }
}
