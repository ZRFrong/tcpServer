package com.webSocket;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

public class http2 {

//*
//     * GET---有参测试 (方式一:手动在url后面加上参数)
//     *



        @Test
        public void doGetTestWayOne() throws InterruptedException {
            while (true) {
                // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();

                // 参数
                StringBuffer params = new StringBuffer();
                try {
                    params.append("address=" + URLEncoder.encode("shanxi-dapeng600_01", "utf-8"));
                    params.append("&");
                    params.append("text=" + "08,");
                    Random random = new Random();
                    int i = random.nextInt(12);
                    String i3;
                    if (i < 10) {
                        i3 = "0" + (i + "");
                    } else {
                        i3 = i + "";
                    }
                    String[] arr = new String[]{"00", "255"};
                    int i1 = random.nextInt(2);
                    String i2 = arr[i1];
                    params.append(i3 + "," + i2 + "," + "00");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                // 创建Get请求
                HttpGet httpGet = new HttpGet("http://115.28.136.205:9009/tcp/tcpOrder/query" + "?" + params);
                // 响应模型
                CloseableHttpResponse response = null;
                try {

                    // 配置信息
                    RequestConfig requestConfig = RequestConfig.custom()
                            // 设置连接超时时间(单位毫秒)
                            .setConnectTimeout(5000)
                            // 设置请求超时时间(单位毫秒)
                            .setConnectionRequestTimeout(5000)
                            // socket读写超时时间(单位毫秒)
                            .setSocketTimeout(5000)
                            // 设置是否允许重定向(默认为true)
                            .setRedirectsEnabled(true).build();

                    // 将上面的配置信息 运用到这个Get请求里
                    httpGet.setConfig(requestConfig);

                    // 由客户端执行(发送)Get请求
                    response = httpClient.execute(httpGet);

                    // 从响应模型中获取响应实体
                    HttpEntity responseEntity = response.getEntity();
                    System.out.println("响应状态为:" + response.getStatusLine());
                    if (responseEntity != null) {
                        System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                        System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                    }else {
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // 释放资源
                        if (httpClient != null) {
                            httpClient.close();
                        }
                        if (response != null) {
                            response.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Thread.sleep(10000);
            }
        }
    }

