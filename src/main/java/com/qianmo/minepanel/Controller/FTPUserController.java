package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Entity.FTPUserEntity;
import com.qianmo.minepanel.Service.FTPUserManager;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Resource
    private FTPUserManager ftpUserManager;

    @Autowired
    private DaemonConfiguration daemonConfiguration;

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
        FTPUserEntity ftpUserEntity = new FTPUserEntity();
        ftpUserEntity.setUserid(username);
        ftpUserEntity.setUserpassword(password);
        ftpUserEntity.setHomedirectory(dir);
        ftpUserManager.Add(ftpUserEntity);
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
        ftpUserManager.Delete(username);
        map.put("code", "200");
        map.put("msg", "Success");
        return map;
    }
}
