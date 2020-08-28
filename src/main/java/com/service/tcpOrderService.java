package com.service;

import com.pojo.tcp;
import com.pojo.tcpOrder;

import java.util.List;

public interface tcpOrderService {


    Long save(tcpOrder tcporder1);


    List<tcpOrder> tcpList();


    String selectByResults(String id);


    tcpOrder selectByAdrees(tcpOrder tcpOrder);

    void updataByText(tcpOrder tcpOrder1);
}
