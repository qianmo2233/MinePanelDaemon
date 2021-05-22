package com.qianmo.minepanel.Entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FTP_USER")
@EntityListeners(AuditingEntityListener.class)
public class FTPUser {
    @Id
    @Column(name = "userid")
    private String userid;

    @Column(name = "userpassword")
    private String userpassword;

    @Column(name = "homedirectory", nullable = false)
    private String homedirectory;

    @Column(name = "writepermission")
    private Boolean writepermission = true;

    @Column(name = "idletime")
    private Integer idletime = 0;

    @Column(name = "uploadrate")
    private Integer uploadrate = 0;

    @Column(name = "downloadrate")
    private Integer downloadrate = 0;

    @Column(name = "maxloginnumber")
    private Integer maxloginnumber = 3;

    @Column(name = "maxloginperip")
    private Integer maxloginperip = 3;

    @Column(name = "enableflag")
    private Boolean enableflag = true;
}
