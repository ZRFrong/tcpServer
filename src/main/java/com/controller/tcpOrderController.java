package com.controller;


import com.pojo.TcpType;
import com.pojo.tcpOrder;
import com.service.tcpOrderService;
import com.service.tcpService;
import com.tcp.NettyServer;
import com.utils.Crc16Util;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/tcpOrder")
public class tcpOrderController {


    @Autowired
    private tcpOrderService tcpOrederService1;

    @Autowired
    private tcpService tcpService1;


    @RequestMapping("/query")
    public Long query(tcpOrder tcpOrder) {
        Long save = null;
        String address = tcpOrder.getAddress();
        String[] split2 = address.split("_");
        String address2 = split2[0];
        tcpOrder.setAddress(address2);
        try {
//        text处理
            ArrayList<String> strings1 = new ArrayList<>();
            String text1 = split2[1];
            strings1.add(text1);
            strings1.add("05");
            String text = tcpOrder.getText();
            String[] split3 = text.split(",");
            for (String s : split3) {
                strings1.add(s);
            }
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
            String str = Crc16Util.byteTo16String(data).toUpperCase();
            Map<String, ChannelHandlerContext> map = NettyServer.map;
            ChannelHandlerContext no1_1 = map.get(tcpOrder.getAddress());
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
            String s = str.replaceAll("\\s*", "");
            tcpOrder.setText(s);
            tcpOrder.setResults("N");
            tcpOrder.setCreateTime(new Date());
            save = tcpOrederService1.save(tcpOrder);
            return save;
        } catch (NumberFormatException e) {
//            删除心跳
//            tcpService1.delectById(tcpOrder.getAddress());
            System.out.println(address + "发送失败" + tcpOrder.getText() + "发送时间" + new Date());
            return 0L;
        }
    }

    @RequestMapping("/query1")
    public Long query1(tcpOrder tcpOrder) {
        Long save = null;
        String address = tcpOrder.getAddress();
        try {
//        text处理
            ArrayList<String> strings1 = new ArrayList<>();

            String text = tcpOrder.getText();
            String[] split3 = text.split(",");
            for (String s : split3) {
                strings1.add(s);
            }
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
            String str = Crc16Util.byteTo16String(data).toUpperCase();
            Map<String, ChannelHandlerContext> map = NettyServer.map;
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
            String s = str.replaceAll("\\s*", "");
            tcpOrder.setText(s);
            tcpOrder.setResults("N");
            tcpOrder.setCreateTime(new Date());
            save = tcpOrederService1.save(tcpOrder);
            return save;
        } catch (NumberFormatException e) {
//            删除心跳
            tcpService1.delectById(address);
            return 0L;
        }
    }

    @RequestMapping("/query2")
    public Long query2(tcpOrder tcpOrder) {
        Long save = null;
        String address = tcpOrder.getAddress();
        String text = tcpOrder.getText();
        try {

            Map<String, ChannelHandlerContext> map = NettyServer.map;
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(text);
            channel.flush();
            String s = text.replaceAll("\\s*", "");
            tcpOrder.setText(s);
            tcpOrder.setResults("N");
            tcpOrder.setCreateTime(new Date());
            save = tcpOrederService1.save(tcpOrder);
            return save;
        } catch (NumberFormatException e) {
//            删除心跳
            tcpService1.delectById(address);
            return 0L;
        }
    }

    /*
     *
     * 温湿度状态查询接口
     * */
    @RequestMapping("/queryState")
    @GetMapping
    public void queryState(String address) {
        String[] split2 = address.split("_");
        String address2 = split2[0];
        String snull = null;
        try {
//        text处理
            ArrayList<String> strings1 = new ArrayList<>();
            int text1 = Integer.parseInt(split2[1]);
            strings1.add((text1 + 99) + "");
            strings1.add("03");
            String text = "00,00,00,02";
            String[] split3 = text.split(",");
            for (String s : split3) {
                strings1.add(s);
            }
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
            String str = Crc16Util.byteTo16String(data).toUpperCase();
            Map<String, ChannelHandlerContext> map = NettyServer.map;
            ChannelHandlerContext no1_1 = map.get(address2);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
            System.out.println(data);
            snull = str.replaceAll("\\s*", "");

//            更新状态
        } catch (NumberFormatException e) {
//            删除心跳
//            tcpService1.delectById(tcpOrder.getAddress());
            System.out.println(address + "查询失败" + snull + "查询时间" + new Date());
        }
    }

    /*
     *
     * 温湿度状态查询接口
     * */
    @RequestMapping("/queryStateLight")
    @GetMapping
    public void queryStateLight(String address) {
        String[] split2 = address.split("_");
        String address2 = split2[0];
        String snull = null;
        try {
//        text处理
            ArrayList<String> strings1 = new ArrayList<>();
            int text1 = Integer.parseInt(split2[1]);
            strings1.add((text1 + 109) + "");
            strings1.add("03");
            String text = "00,06,00,01";
            String[] split3 = text.split(",");
            for (String s : split3) {
                strings1.add(s);
            }
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
            String str = Crc16Util.byteTo16String(data).toUpperCase();
            Map<String, ChannelHandlerContext> map = NettyServer.map;
            ChannelHandlerContext no1_1 = map.get(address2);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
            System.out.println(data);
            snull = str.replaceAll("\\s*", "");

//            更新状态
        } catch (NumberFormatException e) {
//            删除心跳
//            tcpService1.delectById(tcpOrder.getAddress());
            System.out.println(address + "查询失败" + snull + "查询时间" + new Date());
        }
    }


    @RequestMapping("/list")
    public List<tcpOrder> exportExcel() {
        List<tcpOrder> tcps = tcpOrederService1.tcpList();
        return tcps;
    }
    /*
    * 自动开关量状态查询
    * */
    @RequestMapping("inquireState")
    public  Long  inquireState(tcpOrder tcpOrder){
        Long save = null;
        String address = tcpOrder.getAddress();
        String[] split2 = address.split("_");
        String address2 = split2[0];
        tcpOrder.setAddress(address2);
        try {
//        text处理
            ArrayList<String> strings1 = new ArrayList<>();
            String text1 = split2[1];
            strings1.add(text1);
            strings1.add("01");
            String text = tcpOrder.getText();
            String[] split3 = text.split(",");
            for (String s : split3) {
                strings1.add(s);
            }
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
            String str = Crc16Util.byteTo16String(data).toUpperCase();
            Map<String, ChannelHandlerContext> map = NettyServer.map;
            ChannelHandlerContext no1_1 = map.get(tcpOrder.getAddress());
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
            String s = str.replaceAll("\\s*", "");
            tcpOrder.setText(s);
            tcpOrder.setResults("N");
            tcpOrder.setCreateTime(new Date());
            save = tcpOrederService1.save(tcpOrder);
            return save;
        } catch (NumberFormatException e) {
//            删除心跳
//            tcpService1.delectById(tcpOrder.getAddress());

            return 0L;
        }

    }




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


}
