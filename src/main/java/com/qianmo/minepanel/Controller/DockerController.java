package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Docker.DockerManager;
import com.qianmo.minepanel.Response.DockerContainersResponse;
import com.qianmo.minepanel.Response.DockerImagesResponse;
import com.qianmo.minepanel.Response.NoDockerResponse;
import com.qianmo.minepanel.Response.UnauthorizedResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getImages(@QueryParam("token") String token) {
        if(!daemonConfiguration.getToken().equals(token)) return new UnauthorizedResponse().get();
        if(!DockerManager.getEnable()) return new NoDockerResponse().get();
        return new DockerImagesResponse(dockerManager.getImages()).get();
    }

    @GET
    @Path("containers")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainers(@QueryParam("token") String token) {
        if(!daemonConfiguration.getToken().equals(token)) return new UnauthorizedResponse().get();
        if(!DockerManager.getEnable()) return new NoDockerResponse().get();
        return new DockerContainersResponse(dockerManager.getContainers()).get();
    }
}
