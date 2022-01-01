package com.qianmo.minepanel.Config;

import com.qianmo.minepanel.Controller.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(DaemonController.class);
        register(DockerController.class);
        register(ServerController.class);
        register(FTPUserController.class);
        register(SessionController.class);
    }
}
