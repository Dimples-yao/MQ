package com.clinet;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaClinet {
    public static void main(String[] args) {
        /*1. 建立一个Connector*/
        NioSocketConnector nioSocketConnector = new NioSocketConnector();
        /*2. 设置处理消息的handler*/
        nioSocketConnector.setHandler(new MinaClinetHandler());
        /*3. 设置过滤器*/
        nioSocketConnector.getFilterChain().addLast("minaClinet",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
        /*4. 连接服务器*/
        ConnectFuture connectFuture = nioSocketConnector.connect(new InetSocketAddress("127.0.0.1",9999));
        /*阻塞等待连接创建*/
        connectFuture.awaitUninterruptibly();

        BufferedReader inputReader = null ;

        try {
            inputReader = new BufferedReader(new InputStreamReader(System.in,"utf-8"));//从控制台读取内容
            String s;
            while (!(s = inputReader.readLine()).equals("exit")){
                sendMsg(connectFuture.getSession(), "消息测试--"+s);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void sendMsg(IoSession session , String msg){
        if(session != null){
            session.write(msg);
        }
    }
}
