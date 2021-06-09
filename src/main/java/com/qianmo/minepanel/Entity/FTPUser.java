package com.qianmo.minepanel.Entity;

import lombok.Data;

@Data
public class FTPUser {
    private String userid;
    private String userpassword;
    private String homedirectory;
    private Boolean writepermission = true;
    private Integer idletime = 0;
    private Integer uploadrate = 0;
    private Integer downloadrate = 0;
    private Integer maxloginnumber = 3;
    private Integer maxloginperip = 3;
    private Boolean enableflag = true;
}
