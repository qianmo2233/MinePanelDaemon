package com.qianmo.minepanel;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "daemon.config")
public class DaemonConfiguration {
    private String token;

    private Integer ftp_port;

    private String docker;
}
