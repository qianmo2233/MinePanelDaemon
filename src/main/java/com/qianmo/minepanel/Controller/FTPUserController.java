package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Service.Manager.FTPUserManager;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Component
@RestController
@Path("/ftp")
public class FTPUserController {
    private final FTPUserManager ftpUserManager;

    private final DaemonConfiguration daemonConfiguration;

    public FTPUserController(FTPUserManager ftpUserManager, DaemonConfiguration daemonConfiguration) {
        this.ftpUserManager = ftpUserManager;
        this.daemonConfiguration = daemonConfiguration;
    }

    @GET
    @Path("add")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> Add(@QueryParam("username") String username, @QueryParam("password") String password, @QueryParam("dir") String dir, @QueryParam("token") String token)throws Exception {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        ftpUserManager.add(username, password, dir);
        map.put("code", "200");
        map.put("msg", "Success");
        return map;
    }

    @GET
    @Path("delete")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> Delete(@QueryParam("username") String username, @QueryParam("token") String token)throws Exception {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        ftpUserManager.del(username);
        map.put("code", "200");
        map.put("msg", "Success");
        return map;
    }
}
