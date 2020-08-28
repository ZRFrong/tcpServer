package com.controller;

import com.pojo.tcp;
import com.service.tcpService;
import com.tcp.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/tcpClient")
public class tcpController {


    @Autowired
    private tcpService tcpService1;

    @PostMapping("/accept")
    public List<tcp> exportExcel() throws IOException {
        List<tcp> tcps = tcpService1.tcpList();
        return tcps;
    }

    @PostConstruct
    public void listen() throws Exception {

        new Thread(){
            public void run(){
                System.out.println("启动监听");//这里是线程需要做的事情
                try {
                    NettyServer.bind(12307);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @RequestMapping("/index")
    public String ftlIndex() {
        return "index";
    }




}
