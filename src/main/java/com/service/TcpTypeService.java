package com.service;

import com.pojo.TcpType;

import java.util.Date;
import java.util.List;

public interface TcpTypeService {
    boolean serverByAddress(TcpType tcpType);

    void save(TcpType tcpType);

    void updateByAddress(TcpType tcpType);

    void close(String key);

    List<TcpType> selectList();

    void updateByAddressLight(TcpType tcpTypeLight);
}
