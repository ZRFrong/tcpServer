package com.dao;


import com.pojo.tcpOrder;
import org.apache.ibatis.annotations.*;


@Mapper
public interface tcpOrderDao {

    @Insert({"insert into db_tcp_order(address, text,create_time,codeonly,code,results) values( #{address}," +
            "#{text},#{createTime},#{codeonly},#{code},#{results})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(tcpOrder tcpOrder);


    @Select("select * from db_tcp_order where address = #{address} and is_send=0")
    tcpOrder selectByAddress(String address);


    @Update("update db_tcp_order set code=#{code} where codeonly=#{codeonly}")
    void Recentlysave(tcpOrder tcpOrder);

    @Update("update db_tcp_order set results=#{results} where codeonly=#{codeonly}")
    void Resultssave(tcpOrder tcpOrder);

    @Select("select * from db_tcp_order where codeonly = #{codeonly} ")
    tcpOrder selectBycodeOnly(String codeOnly);

    @Select("select * from db_tcp_order where id = #{id} ")
    tcpOrder selectByResults(Long id);

    @Update("update db_tcp_order set results=#{results} where id=#{id}")
    void updataByText(tcpOrder tcpOrder);

    @Select("select * from db_tcp_order where text = #{text}and  address = #{address}  order by create_time desc LIMIT 1")
    tcpOrder selectByAdrees(tcpOrder tcpOrder);
}
