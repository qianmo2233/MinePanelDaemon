package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.Container.ContainerManager;
import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Service.Manager.ServerManager;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@Path("/server")
public class ServerController {
    private final ServerManager serverManager;

    private final DaemonConfiguration daemonConfiguration;

    public ServerController(ServerManager serverManager, DaemonConfiguration daemonConfiguration) {
        this.serverManager = serverManager;
        this.daemonConfiguration = daemonConfiguration;
    }

    @GET
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createServer(
            @QueryParam("memory") Integer memory,
            @QueryParam("disk") Integer disk,
            @QueryParam("token") String token,
            @QueryParam("port") Integer port,
            @QueryParam("image") String image,
            @QueryParam("file") String file,
            @QueryParam("cmd") String cmd,
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
        serverManager.add(memory, disk, port, image, file, cmd, cpu, autorun, docker);
        map.put("code", "200");
        map.put("msg", "Success");
        return map;
    }

    @GET
    @Path("delete")
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
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> startServer(@QueryParam("id") Integer id, @QueryParam("token") String token) throws Exception {
        Map<String, String> map = new HashMap<>();
        if(!daemonConfiguration.getToken().equals(token)) {
            map.put("code", "401");
            map.put("msg", "Access Denied");
            return map;
        }
        Boolean result = serverManager.start(id);
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
