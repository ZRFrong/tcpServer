package com.tcp;


import com.pojo.TcpType;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 *
 * 解析温湿度
 * */
public class ParsingType {

    public static TcpType getTcpType(String typeString) {
        char[] chars = typeString.toCharArray();
        List<String> strings = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (char aChar : chars) {
            if (i > 0 && i < 16) {
                if (i % 2 == 0) {
                    String s = stringBuilder.toString();
                    strings.add(s);
                    stringBuilder.setLength(0);
                }
                stringBuilder.append(aChar);
            } else if (i == 0) {
                stringBuilder.append(aChar);
            }
            i++;
        }
        TcpType tcpType = new TcpType();
        /*
         * 温度
         * */
        stringBuilder.setLength(0);
        String s = strings.get(3);
        String s1 = strings.get(4);
        stringBuilder.append(s);
        stringBuilder.append(s1);
        String temperature = stringBuilder.toString();
        long tmpNum = Long.parseLong(temperature, 16);
        /*
         * 温度有负值
         * */
        String temperatureNow = new String();
        if (tmpNum > 32768) {
//    负值
            tmpNum = tmpNum - 65535;
            float getfloat = getfloat(tmpNum);
            temperatureNow = "-" + getfloat;
        } else {
            float getfloat = getfloat(tmpNum);
            temperatureNow = "" + getfloat;
        }
        tcpType.setTemperature(temperatureNow);
        /*
         * 湿度
         * */
        stringBuilder.setLength(0);
        String s2 = strings.get(5);
        String s3 = strings.get(6);
        stringBuilder.append(s2);
        stringBuilder.append(s3);
        String humidity = stringBuilder.toString();
        long humNum = Long.parseLong(humidity, 16);
        float getfloat = getfloat(humNum);
        humidity = "" + getfloat;
        tcpType.setHumidity(humidity);
        /*
         * 设备号
         * */
        String s5 = strings.get(0);
        long addressInt = Long.parseLong(s5, 16);
        String addressNum = new String();
        addressNum = "_0" + (addressInt - 99);
        tcpType.setAddress(addressNum);
        /*
         * 返回
         * */
        return tcpType;
    }

    public static float getfloat(long sor) {

        int i = Integer.parseInt(String.valueOf(sor));
        DecimalFormat df = new DecimalFormat("0.00");//设置保留位数
        float v = (float) i / 100;
        System.out.println(v);
        return v;
    }


    public static TcpType getTcpTypeLight(String typeString) {
        char[] chars = typeString.toCharArray();
        List<String> strings = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (char aChar : chars) {
            if (i > 0 && i < 16) {
                if (i % 2 == 0) {
                    String s = stringBuilder.toString();
                    strings.add(s);
                    stringBuilder.setLength(0);
                }
                stringBuilder.append(aChar);
            } else if (i == 0) {
                stringBuilder.append(aChar);
            }
            i++;
        }
        TcpType tcpType = new TcpType();
        stringBuilder.setLength(0);
        String s = strings.get(3);
        String s1 = strings.get(4);
        stringBuilder.append(s);
        stringBuilder.append(s1);
        String temperature = stringBuilder.toString();
        Long tmpNum = Long.parseLong(temperature, 16);
        tcpType.setLight(tmpNum.toString());
        String s5 = strings.get(0);
        long addressInt = Long.parseLong(s5, 16);
        String addressNum = new String();
        addressNum = "_0" + (addressInt - 109);
        tcpType.setAddress(addressNum);
      return  tcpType;
    }


    public static void main(String[] args) {
        TcpType tcpTypeLight = getTcpTypeLight("6E0302827A8D0E");
        System.out.println(tcpTypeLight.getLight());
    }

}
