package com.qianmo.minepanel.Controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Component
@RestController
@Path("/daemon")
public class DaemonController {
    @GET
    @Path("info")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("version", "0.0.1");
        map.put("api", "0.0.1");
        return map;
    }
}
