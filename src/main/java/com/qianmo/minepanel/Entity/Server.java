package com.qianmo.minepanel.Entity;

import lombok.Data;

@Data
public class Server {
    private Integer id;
    private Integer memory;
    private Integer disk;
    private Boolean autorun = true;
    private String param = "";
    private String file = "";
}
