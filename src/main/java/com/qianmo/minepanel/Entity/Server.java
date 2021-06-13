package com.qianmo.minepanel.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("server")
public class Server {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("memory")
    private Integer memory;

    @TableField("disk")
    private Integer disk;

    @TableField("autorun")
    private Boolean autorun = true;

    @TableField("param")
    private String param = "";

    @TableField("file")
    private String file = "";

    @TableField("docker")
    private Boolean docker;

    @TableField("container")
    private String container;

    @TableField("port")
    private Integer port;

    @TableField("image")
    private  String image;

    @TableField("cpu")
    private Integer cpu;
}
