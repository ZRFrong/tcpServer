package com.dao;


import com.pojo.tcp;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface tcpDao {


    @Select("select * from db_tcp_client where id = #{id}")
    tcp tcpByid(Long id);


    @Select("select * from db_tcp_client")
    List<tcp> tcpList();


    @Insert({"insert into db_tcp_client(ip, duankou," +
            "address) values( #{ip}, #{duankou},#{address})"})
    void save(tcp tcp);

    @Select("select * from db_tcp_client where ip=#{clientIp} and islongin=1 and duankou=#{duankou}")
    List<tcp> savebyip(@Param("clientIp") String clientIp, @Param("duankou") String duankou);

    @Delete("delete from db_tcp_client where ip=#{clientIp} and duankou=#{duankou}")
    void updatebyip(@Param("clientIp") String ip, @Param("duankou") String duankou);

    @Update("update db_tcp_client set address=#{address}")
    void updatebyiptext(String msg1);

    @Select("select * from db_tcp_client where address=#{address}")
    tcp selectbyaddress(@Param("address") String address);

    @Update("update db_tcp_client set ip=#{ip},duankou=#{duankou} where address=#{address}")
    void updatebyaddress(tcp tcp);

    @Update("update db_tcp_client set heartbeatTime=#{heartbeatTime} where address=#{address}")
    void updatebyaddressTime(tcp tcp);

    @Delete("delete from db_tcp_client  where address=#{address}")
    void delectById(String address);


}
