package com.qianmo.minepanel.Config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.qianmo.minepanel.Controller");
    }
}
