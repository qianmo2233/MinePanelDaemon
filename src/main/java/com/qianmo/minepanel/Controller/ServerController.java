package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Entity.Server;
import com.qianmo.minepanel.Service.ServerManager;
import com.qianmo.minepanel.Utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Component
@RestController
@Path("/server")
public class ServerController {
    @Resource
    private ServerManager serverManager;

    @Autowired
    private DaemonConfiguration daemonConfiguration;

    @GET
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createServer(@QueryParam("id") Integer id, @QueryParam("memory") Integer memory, @QueryParam("disk") Integer disk, @QueryParam("token") String token) throws Exception {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        File file = new File("data/servers/" + id + "/");
        if(file.exists() || serverManager.getServer(id) != null) {
            map.put("code", "201");
            map.put("msg", "Server already exists");
        } else {
            Server server = new Server();
            server.setId(id);
            server.setMemory(memory);
            server.setDisk(disk);
            serverManager.Add(server);
            file.mkdirs();
            map.put("code", "200");
            map.put("msg", "Success");
        }
        return map;
    }

    @GET
    @Path("delete")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> deleteServer(@QueryParam("id") Integer id, @QueryParam("token") String token) throws Exception {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        File file = new File("data/servers/" + id + "/");
        if(!file.exists() || serverManager.getServer(id) == null) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else {
            serverManager.Delete(id);
            Common.deleteAll("data/servers/" + id + "/");
            map.put("code", "200");
            map.put("msg", "Success");
        }
        return map;
    }
}
