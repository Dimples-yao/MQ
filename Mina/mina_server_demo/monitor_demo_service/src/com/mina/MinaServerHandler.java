package com.mina;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Mina框架中处理消息的handler
 */
public class MinaServerHandler implements IoHandler {
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("MinaServerHandler---sessionClosed");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("MinaServerHandler---sessionCreated");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("MinaServerHandler---sessionIdle");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("MinaServerHandler---sessionOpened");
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        System.out.println("MinaServerHandler---exceptionCaught");
    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        System.out.println("MinaServerHandler---messageReceived"+o.toString());
        ioSession.write("server replay--"+o);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("MinaServerHandler---messageSent:"+message.toString());
    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {

    }
}
