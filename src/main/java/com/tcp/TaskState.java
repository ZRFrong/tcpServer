package com.tcp;

import com.controller.tcpOrderController;
import com.pojo.TcpType;
import com.pojo.tcp;
import com.service.TcpTypeService;
import com.service.tcpService;
import com.utils.Crc16Util;
import com.utils.SpringUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.util.*;

public class TaskState {

    private static ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private static TcpTypeService tcpTypeService = applicationContext.getBean(TcpTypeService.class);
    private static tcpService tcpService = applicationContext.getBean(tcpService.class);
    private static tcpOrderController tcpOrderControlle = applicationContext.getBean(tcpOrderController.class);

    public void HearbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            // 重写线程
            @Override
            public void run() {

                try {
                    openStart();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
// 启动定时器 十分钟执行一次
        timer.schedule(timerTask, 0, 600000);
    }

    ;

    public static String[] to_byte(String[] strs) {
        String[] bytes = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            try {
                int i1 = Integer.parseInt(strs[i]);
                bytes[i] = String.valueOf(i1);
            } catch (NumberFormatException e) {
                bytes[i] = String.valueOf(strs[i]);
//                e.printStackTrace();
            }
        }
        return bytes;
    }

    public static byte[] totext(List strings1) {
        //  text处理

        Object[] objects = strings1.toArray();
        String[] split = new String[objects.length];
        for (int i = 0; i < split.length; i++) {
            split[i] = objects[i].toString();
        }
        List<String> strings = new ArrayList<>();
        for (String s : split) {
            int i = Integer.parseInt(s);
            //            十六进制转成十进制
            String tmp = StringUtils.leftPad(Integer.toHexString(i).toUpperCase(), 4, '0');
            strings.add(tmp);
        }
//        List<String> strings1 = new ArrayList<>();
        String[] split1 = strings.toArray(new String[strings.size()]);
        String[] bytes = to_byte(split1);
//        String[] toBeStored = strings1.toArray(new String[strings1.size()]);
        byte[] data = Crc16Util.getData(bytes);
        return data;
    }

    public void openStart() throws InterruptedException {
        open();
        Thread.sleep(1000);
        open2();
    }


    public void open() {
        List<tcp> tcps = tcpService.tcpList();
        tcps.forEach(tcp -> {
            if (tcp.getAddress().contains("pisitai")) {
                String address2 = tcp.getAddress();
                String snull = null;
                ArrayList<String> strings1 = new ArrayList<>();
                ArrayList<String> strings2 = new ArrayList<>();
                int text1 = Integer.parseInt("1");
                strings1.add((text1 + 99) + "");
                strings1.add("03");
                String text = "00,00,00,02";
                String[] split3 = text.split(",");
                for (String s : split3) {
                    strings1.add(s);
                }
                strings2.add((text1 + 109) + "");
                strings2.add("03");
                String text3 = "00,06,00,01";
                String[] split4 = text3.split(",");
                for (String s : split4) {
                    strings2.add(s);
                }
                byte[] totext = totext(strings1);
                byte[] totext2 = totext(strings2);
                try {
                    String str = Crc16Util.byteTo16String(totext).toUpperCase();
                    String str2 = Crc16Util.byteTo16String(totext).toUpperCase();
                    Map<String, ChannelHandlerContext> map = NettyServer.map;
                    ChannelHandlerContext no1_1 = map.get(address2);
                    Channel channel = no1_1.channel();
                    channel.write(Unpooled.copiedBuffer(totext));
                    channel.flush();
                    snull = str.replaceAll("\\s*", "");

//                  更新状态


                } catch (NumberFormatException e) {
//            删除心跳
//            tcpService1.delectById(tcpOrder.getAddress());
                    System.out.println(tcp.getAddress() + "查询失败" + snull + "查询时间" + new Date());
                }

            }
        });
    }

    public void open2() {
        List<tcp> tcps = tcpService.tcpList();
        tcps.forEach(tcp -> {
            if (tcp.getAddress().contains("pisitai")) {
                String address2 = tcp.getAddress();
                String snull = null;
                ArrayList<String> strings1 = new ArrayList<>();
                ArrayList<String> strings2 = new ArrayList<>();
                int text1 = Integer.parseInt("1");
                strings1.add((text1 + 99) + "");
                strings1.add("03");
                String text = "00,00,00,02";
                String[] split3 = text.split(",");
                for (String s : split3) {
                    strings1.add(s);
                }
                strings2.add((text1 + 109) + "");
                strings2.add("03");
                String text3 = "00,06,00,01";
                String[] split4 = text3.split(",");
                for (String s : split4) {
                    strings2.add(s);
                }
                byte[] totext2 = totext(strings2);
                try {
                    String str2 = Crc16Util.byteTo16String(totext2).toUpperCase();
                    Map<String, ChannelHandlerContext> map = NettyServer.map;
                    ChannelHandlerContext no1_2 = map.get(address2);
                    Channel channe2 = no1_2.channel();
                    channe2.write(Unpooled.copiedBuffer(totext2));
                    channe2.flush();
                    snull = str2.replaceAll("\\s*", "");

//                  更新状态


                } catch (NumberFormatException e) {
//            删除心跳
//            tcpService1.delectById(tcpOrder.getAddress());
                    System.out.println(tcp.getAddress() + "查询失败" + snull + "查询时间" + new Date());
                }

            }
        });
    }

}