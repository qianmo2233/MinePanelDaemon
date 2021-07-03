package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.Container.ContainerManager;
import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Service.Manager.ServerManager;
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
    @Autowired
    private ServerManager serverManager;

    @Autowired
    private DaemonConfiguration daemonConfiguration;

    @GET
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createServer(
            @QueryParam("memory") Integer memory,
            @QueryParam("disk") Integer disk,
            @QueryParam("token") String token,
            @QueryParam("port") Integer port,
            @QueryParam("image") String image,
            @QueryParam("file") String file,
            @QueryParam("param") String param,
            @QueryParam("cpu") Integer cpu,
            @QueryParam("autorun") Boolean autorun,
            @QueryParam("docker") Boolean docker
    ) throws Exception {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        serverManager.add(memory, disk, port, image, file, param, cpu, autorun, docker);
        map.put("code", "200");
        map.put("msg", "Success");
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
        if(!serverManager.del(id)) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else {
            map.put("code", "200");
            map.put("msg", "Success");
        }
        return map;
    }

    @GET
    @Path("start")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> startServer(@QueryParam("id") Integer id, @QueryParam("token") String token, @QueryParam("cmd") String cmd) throws Exception {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        Boolean result = serverManager.start(id, cmd);
        if(result == null) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else if(result) {
            map.put("code", "200");
            map.put("msg", "Success");
        } else {
            map.put("code", "401");
            map.put("msg", "Server is already running");
        }
        return map;
    }

    @GET
    @Path("log")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Object, Object> getServerLog(@QueryParam("id") Integer id, @QueryParam("token") String token) {
        Map<Object, Object> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        File file = new File("data/servers/" + id + "/");
        if(!file.exists() || serverManager.get(id) == null) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else if(ContainerManager.getContainer().containsKey(id)) {
            map.put("code", "200");
            map.put("msg", ContainerManager.getContainer().get(id).getConsoles());
        } else {
            map.put("code", "201");
            map.put("msg", "Server is already stopped");
        }
        return map;
    }

    @GET
    @Path("stop")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> stopServer(@QueryParam("id") Integer id, @QueryParam("token") String token) {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        Boolean result = serverManager.stop(id);
        if(result == null) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else if(result) {
            ContainerManager.destroy(id);
            map.put("code", "200");
            map.put("msg", "Success");
        } else {
            map.put("code", "201");
            map.put("msg", "Server is already stopped");
        }
        return map;
    }
    @GET
    @Path("exec")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> ExecCmd(@QueryParam("id") Integer id, @QueryParam("token") String token, @QueryParam("cmd") String cmd) {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        Boolean result = serverManager.exec(id, cmd);
        if(result == null) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else if(result) {
            ContainerManager.execute(id, cmd);
            map.put("code", "200");
            map.put("msg", "Success");
        } else {
            map.put("code", "201");
            map.put("msg", "Server is already stopped");
        }
        return map;
    }
}
