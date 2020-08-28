package com.pojo;

import lombok.Data;

@Data
public class TcpType {

    private Long id;
    private String address;
    private String temperature;
    private String humidity;
    private String light;
}
