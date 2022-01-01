package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.Response.DaemonInfoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Component
@RestController
@Path("/daemon")
public class DaemonController {
    @GET
    @Path("info")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo() {
        return new DaemonInfoResponse().get();
    }
}
