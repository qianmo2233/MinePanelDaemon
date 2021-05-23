package com.qianmo.minepanel.Entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Server")
@EntityListeners(AuditingEntityListener.class)
public class Server {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "memory")
    private Integer memory;

    @Column(name = "disk")
    private Integer disk;

    @Column(name = "autorun")
    private Boolean autorun = true;

    @Column(name = "param")
    private String param = "";

    @Column(name = "file")
    private String file = "";
}
