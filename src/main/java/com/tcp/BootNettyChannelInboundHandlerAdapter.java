package com.tcp;


import com.pojo.TcpType;
import com.pojo.tcp;
import com.pojo.tcpOrder;
import com.service.TcpTypeService;
import com.service.tcpOrderService;
import com.service.tcpService;
import com.utils.HexConvert;
import com.utils.SpringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

/**
 * I/O数据读写处理类
 */
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {


    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private tcpService tcpService1 = applicationContext.getBean(tcpService.class);
    private TcpTypeService tcpTypeService = applicationContext.getBean(TcpTypeService.class);
    private tcpOrderService tcpOrderService = applicationContext.getBean(tcpOrderService.class);


    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        String s1 = HexConvert.convertStringToHex((String) msg);
//        System.out.println(msg.toString());
//        System.out.println(msg);
        if ((msg + "").contains("dapeng")) {
//            心跳
            InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String clientIp = insocket.getAddress().getHostAddress();
            int port = insocket.getPort();
//
            if (tcpService1.savebyip(msg + "") == null) {
//                                没有
//            添加map管理，添加在线设备
                NettyServer.map.put(msg + "", ctx);
                tcp tcp = new tcp();
                tcp.setAddress(msg + "");
                tcp.setIp(clientIp);
                tcp.setDuankou(port + "");
                tcpService1.save(tcp);
                //              查询状态  温湿度
                TaskState hearbeatRun = new TaskState();
                hearbeatRun.openStart();

            } else {
                //                添加心跳时间
                NettyServer.map.put(msg + "", ctx);
//                覆盖通道
                tcp tcp = new tcp();
                tcp.setHeartbeatTime(new Date());
                tcp.setAddress(msg + "");
                tcpService1.updatebyaddressTime(tcp);
            }

        } else {
//            消息返回状态.
//            String s = Crc16Util.byteTo16String((Byte) msg);
            String s = msg.toString();
            String replace = s.replace("\\s*", "");

            /*
             * 状态响应还是指令返回
             * */
            String address = null;
            for (String key : NettyServer.map.keySet()) {
                if (NettyServer.map.get(key) != null && NettyServer.map.get(key).equals(ctx)) {
                    address = key;
                }
            }
            if (replace.contains("0304")) {
                /*
                03代表查询返回  04代表四个字节  即温湿度返回
                * */
                TcpType tcpType = ParsingType.getTcpType(replace);
                String address1 = tcpType.getAddress();
                tcpType.setAddress(address + address1);
                boolean b = tcpTypeService.serverByAddress(tcpType);
                if (b) {
                    tcpTypeService.updateByAddress(tcpType);
                } else {
                    tcpTypeService.save(tcpType);
                }

            } else if (replace.contains("0302")) {

                  /*
                03代表查询返回  02代表四个字节  即光照返回
                * */
                TcpType tcpTypeLight = ParsingType.getTcpTypeLight(replace);
                tcpTypeLight.setAddress(address + tcpTypeLight.getAddress());
                boolean b = tcpTypeService.serverByAddress(tcpTypeLight);
                System.out.println("光照返回" + tcpTypeLight.getLight());
                if (b) {
                    tcpTypeService.updateByAddressLight(tcpTypeLight);
                } else {
                    tcpTypeService.save(tcpTypeLight);
                }


            } else {

                //            获取地址值
                tcpOrder tcpOrder = new tcpOrder();
                tcpOrder.setAddress(address);

                tcpOrder.setText(replace);
                tcpOrder.setResults("N");
                tcpOrder tcpOrder1 = tcpOrderService.selectByAdrees(tcpOrder);
//            根据返回的值处理
                if (tcpOrder1 != null) {
                    tcpOrder1.setResults("Y");
                    System.out.println("地址:" + tcpOrder1.getAddress() + "指令:" + tcpOrder1.getText() + "时间" + new Date());
                    tcpOrderService.updataByText(tcpOrder1);
                }

            }
            tcp tcp = new tcp();
            tcp.setHeartbeatTime(new Date());
            tcp.setAddress(address);
            tcpService1.updatebyaddressTime(tcp);

        }

    }

    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
//        System.out.println("接受成功");


        ctx.flush();
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();

        for (String key : NettyServer.map.keySet()) {
            if (NettyServer.map.get(key) != null && NettyServer.map.get(key).equals(ctx)) {
                tcpService1.delectById(key);
                NettyServer.map.remove(key);
                tcpTypeService.close(key);
                System.out.println(new Date() + key + "客户端异常断开连接:" + clientIp);
            }
        }
        ctx.close();//抛出异常，断开与客户端的连接
    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {


    }

    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();

        for (String key : NettyServer.map.keySet()) {
            if (NettyServer.map.get(key) != null && NettyServer.map.get(key).equals(ctx)) {
                tcpService1.delectById(key);
                NettyServer.map.remove(key);
                tcpTypeService.close(key);
                System.out.println(new Date() + key + "客户端断开连接:" + clientIp);
            }
        }

        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
    }

    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception, IOException {
        super.userEventTriggered(ctx, evt);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close();//超时时断开连接
        System.out.println("userEventTriggered:" + clientIp);
    }
}
