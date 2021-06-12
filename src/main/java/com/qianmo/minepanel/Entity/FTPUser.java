package com.qianmo.minepanel.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ftp_user")
public class FTPUser {
    @TableId
    private String userid;

    @TableField("userpassword")
    private String userpassword;

    @TableField("homedirectory")
    private String homedirectory;

    @TableField("writepermission")
    private Boolean writepermission = true;

    @TableField("idletime")
    private Integer idletime = 0;

    @TableField("uploadrate")
    private Integer uploadrate = 0;

    @TableField("downloadrate")
    private Integer downloadrate = 0;

    @TableField("maxloginnumber")
    private Integer maxloginnumber = 3;

    @TableField("maxloginperip")
    private Integer maxloginperip = 3;

    @TableField("enableflag")
    private Boolean enableflag = true;
}
