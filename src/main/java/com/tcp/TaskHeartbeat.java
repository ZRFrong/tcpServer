package com.tcp;


import com.pojo.tcp;
import com.service.tcpService;
import com.utils.DateUtils;
import com.utils.SpringUtils;
import org.springframework.context.ApplicationContext;

import java.util.*;

/*
 * 心跳超时删除
 * */
public class TaskHeartbeat {

    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private tcpService tcpService1 = applicationContext.getBean(tcpService.class);

    public void HearbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            // 重写线程
            @Override
            public void run() {
//                遍历查询心跳时间
                List<tcp> tcps = tcpService1.tcpList();
                if (tcps!=null){
                    for (tcp tcp : tcps) {
                            Date heartbeatTime = tcp.getHeartbeatTime();
                            if (heartbeatTime!=null){
                                Long minuteDiff = DateUtils.getMinuteDiff(heartbeatTime, new Date());
                                if (minuteDiff>=1){
                                    tcpService1.delectById(tcp.getAddress());
                                }
                            }else {
                                tcpService1.delectById(tcp.getAddress());
                            }
                        }
                }
            }
        };
        // 启动定时器 一分钟执行一次
        timer.schedule(timerTask, 0, 60000);
    };
}


