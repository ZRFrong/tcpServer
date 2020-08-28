//package com.webSocket;
//
//import java.util.*;
//
//import com.service.tcpOrderService;
//import com.utils.SpringUtils;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
///**
// * @Author：JCccc
// * @Description：
// * @Date： created in 15:56 2019/5/13
// */
//@Component
//public class ProductExpireTask {
//
//    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
//    private tcpOrderService tcpOrderService1 = applicationContext.getBean(tcpOrderService.class);
//
//
//    /**
//     * 定时响应状态
//     */
//    public java.lang.String timer1(String id) {
//        // 定时器对象
//        Timer timer = new Timer();
//        // 定时器任务对象
//        TimerTask timerTask = new TimerTask() {
//
//            // 重写线程
//            @Override
//            public void run() {
//
//               boolean v= tcpOrderService1.selectByResults( id);
//                if (v){
//                    timer.cancel();//停止计时器任务
//                }
//            }
//        };
//        // 启动定时器 1秒执行一次
//        timer.schedule(timerTask, 0, 1000);
//        return "ok";
//
//    }
//
//
//
//}