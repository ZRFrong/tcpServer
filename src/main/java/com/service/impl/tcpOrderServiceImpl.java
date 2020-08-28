package com.service.impl;

import com.dao.tcpDao;
import com.dao.tcpOrderDao;
import com.pojo.tcp;
import com.pojo.tcpOrder;
import com.service.tcpOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class tcpOrderServiceImpl implements tcpOrderService {


    @Autowired
    private tcpOrderDao tcpOrderDao1;
    @Autowired
    private tcpDao tcpDao1;

    @Override
    public Long save(tcpOrder tcporder1) {
        tcpOrderDao1.save(tcporder1);
        Long id = tcporder1.getId();
        return id;
    }

    @Override
    public List<tcpOrder> tcpList() {
        return null;
    }

    @Override
    public String selectByResults(String id) {
        long l = Long.parseLong(id);//返回Long包装类型
        tcpOrder tcpOrder = tcpOrderDao1.selectByResults(l);
        if (tcpOrder != null) {
            if (tcpOrder.getResults() != null && !"".equals(tcpOrder.getResults())) {
                return "ok";
            } else {
                return "no";
            }
        } else {
            return "no";
        }
    }

    @Override
    public tcpOrder selectByAdrees(tcpOrder tcpOrder) {
        return tcpOrderDao1.selectByAdrees(tcpOrder);
    }

    @Override
    public void updataByText(tcpOrder tcpOrder1) {
        tcpOrderDao1.updataByText(tcpOrder1);
    }


}
