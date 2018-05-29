package com.mina;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaServer {
    public static void main(String[] args) {
        try {
            /*1. 创建一个accepter对象*/
            NioSocketAcceptor nioSocketAcceptor = new NioSocketAcceptor();
            /*2. 设置Handler*/
            nioSocketAcceptor.setHandler(new MinaServerHandler());
            /*3. 设置拦截器,设置成自带拦截器*/
            nioSocketAcceptor.getFilterChain().addLast("mina", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
            /*4. 创建端口连接*/
            nioSocketAcceptor.bind(new InetSocketAddress(9999));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
