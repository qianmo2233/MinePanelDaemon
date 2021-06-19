package com.qianmo.minepanel.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ftp_user")
public class FTPUserEntity {
    @Id
    private String userid;

    @Column
    private String userpassword;

    @Column
    private String homedirectory;

    @Column
    private Boolean writepermission = true;

    @Column
    private Integer idletime = 0;

    @Column
    private Integer uploadrate = 0;

    @Column
    private Integer downloadrate = 0;

    @Column
    private Integer maxloginnumber = 3;

    @Column
    private Integer maxloginperip = 3;

    @Column
    private Boolean enableflag = true;
}
