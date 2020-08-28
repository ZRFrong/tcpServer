package com.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class tcp {


    private  Long id;
    private  String ip;
    private  String duankou;
    private  String address;
    private Date heartbeatTime;

}
