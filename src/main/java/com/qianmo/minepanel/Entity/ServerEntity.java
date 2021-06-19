package com.qianmo.minepanel.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "server")
public class ServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer memory;

    @Column
    private Integer disk;

    @Column
    private Boolean autorun = true;

    @Column
    private String param = "";

    @Column
    private String file = "";

    @Column
    private Boolean docker;

    @Column
    private String container;

    @Column
    private Integer port;

    @Column
    private  String image;

    @Column
    private Integer cpu;
}
