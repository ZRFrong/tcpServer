//package com.utils;
//
//import com.service.tcpOrderService;
//import com.service.tcpService;
//import com.tcp.NettyServer;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import org.springframework.context.ApplicationContext;
//
//import java.util.*;
//
//public class Task {
//
////    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
////    private tcpService tcpService1 = applicationContext.getBean(tcpService.class);
////    private tcpOrderService tcpOrderService1 = applicationContext.getBean(tcpOrderService.class);
//
//    /**
//     * 定时查询传感器状态
//     */
//    public  void timer1() {
//        // 定时器对象
//        Timer timer = new Timer();
//        // 定时器任务对象
//        TimerTask timerTask = new TimerTask() {
//
//            // 重写线程
//            @Override
//            public void run() {
//                Map<String, ChannelHandlerContext> map = NettyServer.map;
//                Set<String> strings = map.keySet();
//                if (strings!=null) {
//                    strings.forEach(itm -> {
//                        StringBuilder stringBuilder = new StringBuilder();
////            dapengxiangmu;00000001;get_sensor_1;
//                        stringBuilder.append("dapengxiangmu;");
//                        stringBuilder.append(itm + ";");
//                        stringBuilder.append("get_sensor_1;");
//                        String s1 = stringBuilder.toString();
//                        ChannelHandlerContext no1_1 = map.get(itm);
//                        Channel channel = no1_1.channel();
//                        channel.write(s1);
//                        channel.flush();
//                    });
//                }
//            }
//        };
//        // 启动定时器 五分钟执行一次
//        timer.schedule(timerTask, 0, 300000);
//
//
//    }
//
//
//
//
//}
//
//
//
