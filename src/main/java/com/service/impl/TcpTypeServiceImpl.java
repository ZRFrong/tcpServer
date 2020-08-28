package com.service.impl;


import com.dao.TcpTypeDao;
import com.pojo.TcpType;
import com.service.TcpTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TcpTypeServiceImpl implements TcpTypeService {

    @Autowired
    private TcpTypeDao typeDao;

    @Override
    public boolean serverByAddress(TcpType tcpType) {
        TcpType tcpType1 = typeDao.serverByAddress(tcpType.getAddress());
        if (tcpType1 == null) {
            return false;
        }
        return true;
    }



    @Override
    public void save(TcpType tcpType) {
        typeDao.save(tcpType);
    }

    @Override
    public void updateByAddress(TcpType tcpType) {
        typeDao.updateByAddress(tcpType);
    }

    @Override
    public void close(String key) {
        typeDao.updateByAddressToLogin("%"+key+"%");
    }


    @Override
    public void updateByAddressLight(TcpType tcpTypeLight) {
        typeDao.updateByAddressLight(tcpTypeLight);
    }

    /*
    * 列表查询
    * */
    @Override
    public List<TcpType> selectList() {

        return typeDao.selectList();
    }
}
