package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Docker.DockerManager;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Component
@RestController
@Path("/docker")
public class DockerController {
    private final DockerManager dockerManager;

    private final DaemonConfiguration daemonConfiguration;

    public DockerController(DockerManager dockerManager, DaemonConfiguration daemonConfiguration) {
        this.dockerManager = dockerManager;
        this.daemonConfiguration = daemonConfiguration;
    }

    @GET
    @Path("images")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Object, Object> getImages(@QueryParam("token") String token) throws Exception {
        Map<Object, Object> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        map.put("code", 200);
        map.put("images", dockerManager.getImages());
        return map;
    }

    @GET
    @Path("containers")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Object, Object> getContainers(@QueryParam("token") String token) throws Exception {
        Map<Object, Object> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        map.put("code", 200);
        map.put("containers", dockerManager.getContainers());
        return map;
    }
}
