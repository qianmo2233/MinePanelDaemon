package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.Container.ContainerManager;
import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Docker.DockerManager;
import com.qianmo.minepanel.Entity.ServerEntity;
import com.qianmo.minepanel.MinePanelDaemon;
import com.qianmo.minepanel.Service.ServerManager;
import com.qianmo.minepanel.Utils.Common;
import com.qianmo.minepanel.Utils.Docker;
import org.apache.commons.lang.RandomStringUtils;
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

    @Autowired
    private DockerManager dockerManager;

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
        ServerEntity serverEntity = new ServerEntity();
        serverEntity.setMemory(memory);
        serverEntity.setDisk(disk);
        serverEntity.setAutorun(autorun);
        serverEntity.setDocker(docker);
        serverEntity.setCpu(cpu);
        serverEntity.setFile(file);
        serverEntity.setImage(image);
        serverEntity.setParam(param);
        serverEntity.setPort(port);
        Integer id =  serverManager.Add(serverEntity).getId();
        File dir = new File("data/servers/" + id + "/");
        dir.mkdirs();
        if(docker) {
            serverEntity.setContainer(dockerManager.create(image, memory, cpu, port, dir.getAbsolutePath()));
        } else {
            serverEntity.setContainer(RandomStringUtils.random(10));
        }
        serverManager.Update(serverEntity);
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
        if(!new File("data/servers/" + id + "/").exists() || serverManager.getServer(id) == null) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else if(!ContainerManager.getContainer().containsKey(id)) {
            cmd = cmd.replace("file", new File(serverManager.getServer(id).getFile()).getAbsolutePath());
            MinePanelDaemon.getLogger().info(cmd);
            if(serverManager.getServer(id).getDocker()) {
                if (!Docker.hasContainer(serverManager.getServer(id).getContainer(), dockerManager.getContainers())) {
                    dockerManager.create(
                            serverManager.getServer(id).getImage(),
                            serverManager.getServer(id).getMemory(),
                            serverManager.getServer(id).getCpu(),
                            serverManager.getServer(id).getPort(),
                            new File("data/servers/" + id + "/").getAbsolutePath()
                    );
                }
                dockerManager.start(serverManager.getServer(id).getContainer());
                String[] args = new String[0];
                ContainerManager.create(id, "docker attach " + serverManager.getServer(id).getContainer(), args);
                ContainerManager.execute(id, cmd);
            } else {
                String[] args = new String[0];
                ContainerManager.create(id, cmd, args);
            }
            map.put("code", "200");
            map.put("msg", "Success");
        } else {
            map.put("code", "201");
            map.put("msg", "Server is already started");
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
        if(!file.exists() || serverManager.getServer(id) == null) {
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
        File file = new File("data/servers/" + id + "/");
        if(!file.exists() || serverManager.getServer(id) == null) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else if(ContainerManager.getContainer().containsKey(id)) {
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
        File file = new File("data/servers/" + id + "/");
        if(!file.exists() || serverManager.getServer(id) == null) {
            map.put("code", "404");
            map.put("msg", "Server not Found");
        } else if(ContainerManager.getContainer().containsKey(id)) {
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
