package com.qianmo.minepanel.Docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.MinePanelDaemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

//todo Docker
@Component
public class DockerManager {
    private DockerClient dockerClient;

    @Autowired
    private DaemonConfiguration daemonConfiguration;

    public void Init() throws Exception{
        MinePanelDaemon.getLogger().info("Try to connect docker...");
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(daemonConfiguration.getDocker())
                .build();
        dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).build();
        dockerClient.pingCmd().exec();
        Info info = dockerClient.infoCmd().exec();
        MinePanelDaemon.getLogger().info("Successfully connected to docker!");
        MinePanelDaemon.getLogger().info("Docker Version: " + info.getServerVersion());
    }

    public List<SearchItem> searchImages(String keyword) {
        return dockerClient.searchImagesCmd(keyword).exec();
    }

    public Info getInfo() {
        return dockerClient.infoCmd().exec();
    }

    public List<Container> getContainers() {
        return dockerClient.listContainersCmd().exec();
    }

    public String create(String image, Integer memory, Integer cpu, Integer port, String path) {
        Ports portBindings = new Ports();
        portBindings.bind(ExposedPort.tcp(port), Ports.Binding.bindPort(port));
        CreateContainerResponse container = dockerClient.createContainerCmd(image)
                .withMemory((long) (memory * 1024 * 1024))
                .withCpuShares(cpu)
                .withExposedPorts(ExposedPort.tcp(port))
                .withHostConfig(newHostConfig().withPortBindings(portBindings))
                .withVolumes(new Volume(path))
                .withCmd("/bin/bash")
                .exec();
        return container.getId();
    }

    public List<Image> getImages() {
        return dockerClient.listImagesCmd().exec();
    }

    public void start(String id) {
        dockerClient.startContainerCmd(id).exec();
    }

    public void stop(String id) {
        dockerClient.stopContainerCmd(id).exec();
    }

    public void restart(String id) {
        dockerClient.restartContainerCmd(id).exec();
    }

    public void remove(String id) {
        dockerClient.removeContainerCmd(id).exec();
    }
}
