package com.service.impl;


import com.dao.tcpDao;
import com.pojo.tcp;
import com.service.tcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class tcpServiceImpl implements tcpService {

    @Autowired
    private tcpDao tcpDao1;

    @Override
    public List<tcp> tcpList() {
        List<tcp> tcpDaos = tcpDao1.tcpList();
        return tcpDaos;

    }

    @Override
    public tcp tcpByid(Long id) {
        return tcpDao1.tcpByid(id);
    }

    @Override
    public void save(tcp tcp) {
      tcpDao1.save(tcp);
    }
    @Override
    public tcp savebyip(String address) {

        return tcpDao1.selectbyaddress(address);

    }

    @Override
    public void close(String duankou, String clientIp) {
        tcpDao1.updatebyip(duankou, clientIp);
    }

    @Override
    public void delectById(String id) {
        tcpDao1.delectById(id);

    }



    @Override
    public void updatebyaddressTime(tcp tcp) {
        tcpDao1.updatebyaddressTime(tcp);
    }



    @Override
    public void updatebyaddress(tcp tcp) {
        tcpDao1.updatebyaddress(tcp);
    }



}

