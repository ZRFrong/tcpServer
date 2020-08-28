package com.service;

import com.pojo.tcp;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface tcpService {


    List<tcp> tcpList();

    tcp tcpByid(Long id);

    void save(tcp tcp);

    tcp savebyip(String address);

    void close(String duankou, String clientIp);

    void updatebyaddress(tcp tcp);

    void updatebyaddressTime(tcp tcp);


    void delectById(String id);

}
