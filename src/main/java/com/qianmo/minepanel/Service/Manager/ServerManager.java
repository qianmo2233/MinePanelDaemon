package com.qianmo.minepanel.Service.Manager;

import com.qianmo.minepanel.Container.ContainerManager;
import com.qianmo.minepanel.Docker.DockerManager;
import com.qianmo.minepanel.Entity.ServerEntity;
import com.qianmo.minepanel.Service.CRUD.ServerCRUD;
import com.qianmo.minepanel.Utils.Common;
import com.qianmo.minepanel.Utils.Docker;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class ServerManager {
    private final ServerCRUD serverCRUD;

    private final DockerManager dockerManager;

    public ServerManager(ServerCRUD serverCRUD, DockerManager dockerManager) {
        this.serverCRUD = serverCRUD;
        this.dockerManager = dockerManager;
    }

    public ServerEntity add(
            Integer memory,
            Integer disk,
            Integer port,
            String image,
            String file,
            String param,
            Integer cpu,
            Boolean autorun,
            Boolean docker
            ) {
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
        Integer id =  serverCRUD.Add(serverEntity).getId();
        File dir = new File("data/servers/" + id + "/");
        dir.mkdirs();
        if(docker) {
            serverEntity.setContainer(dockerManager.create(image, memory, cpu, port, dir.getAbsolutePath()));
        } else {
            serverEntity.setContainer(RandomStringUtils.random(10));
        }
        return serverCRUD.Update(serverEntity);
    }

    public Boolean del(Integer id) {
        if(new File("data/servers/" + id + "/").exists() || serverCRUD.getServer(id) == null) {
            return false;
        } else {
            serverCRUD.Delete(id);
            Common.deleteAll("data/servers/" + id + "/");
            return true;
        }
    }

    public ServerEntity up(
            Integer id,
            Integer memory,
            Integer disk,
            Integer port,
            String image,
            String file,
            String param,
            Integer cpu,
            Boolean autorun,
            Boolean docker
    ) {
        ServerEntity serverEntity = new ServerEntity();
        serverEntity.setId(id);
        serverEntity.setMemory(memory);
        serverEntity.setDisk(disk);
        serverEntity.setAutorun(autorun);
        serverEntity.setDocker(docker);
        serverEntity.setCpu(cpu);
        serverEntity.setFile(file);
        serverEntity.setImage(image);
        serverEntity.setParam(param);
        serverEntity.setPort(port);
        return serverCRUD.Update(serverEntity);
    }

    public List<ServerEntity> get() {
        return serverCRUD.getAllServer();
    }

    public ServerEntity get(Integer id) {
        return serverCRUD.getServer(id);
    }

    public Boolean start(Integer id, String cmd) {
        if(!new File("data/servers/" + id + "/").exists() || serverCRUD.getServer(id) == null) {
            return null;
        } else if(!ContainerManager.getContainer().containsKey(id)) {
            cmd = cmd.replace("{file}", new File(serverCRUD.getServer(id).getFile()).getAbsolutePath());
            if(serverCRUD.getServer(id).getDocker()) {
                if (!Docker.hasContainer(serverCRUD.getServer(id).getContainer(), dockerManager.getContainers())) {
                    dockerManager.create(
                            serverCRUD.getServer(id).getImage(),
                            serverCRUD.getServer(id).getMemory(),
                            serverCRUD.getServer(id).getCpu(),
                            serverCRUD.getServer(id).getPort(),
                            new File("data/servers/" + id + "/").getAbsolutePath()
                    );
                }
                dockerManager.start(serverCRUD.getServer(id).getContainer());
                String[] args = new String[0];
                ContainerManager.create(id, "docker attach " + serverCRUD.getServer(id).getContainer(), args, serverCRUD.getServer(id).getContainer());
                ContainerManager.execute(id, cmd);
            } else {
                String[] args = new String[0];
                ContainerManager.create(id, cmd, args, serverCRUD.getServer(id).getContainer());
            }
            return true;
        } else {
            return false;
        }
    }

    public Boolean stop(Integer id) {
        if(!new File("data/servers/" + id + "/").exists() || serverCRUD.getServer(id) == null) {
            return null;
        } else if(ContainerManager.getContainer().containsKey(id)) {
            ContainerManager.destroy(id);
            return true;
        } else {
            return false;
        }
    }

    public Boolean exec(Integer id, String cmd) {
        if(new File("data/servers/" + id + "/").exists() || serverCRUD.getServer(id) == null) {
            return null;
        } else if(ContainerManager.getContainer().containsKey(id)) {
            ContainerManager.execute(id, cmd);
            return true;
        } else {
            return false;
        }
    }
}
