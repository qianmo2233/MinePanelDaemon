package com.qianmo.minepanel.Config;

import com.qianmo.minepanel.Controller.DaemonController;
import com.qianmo.minepanel.Controller.DockerController;
import com.qianmo.minepanel.Controller.FTPUserController;
import com.qianmo.minepanel.Controller.ServerController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(DaemonController.class);
        register(FTPUserController.class);
        register(ServerController.class);
        register(DockerController.class);
    }
}
