package com.qianmo.minepanel.Docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.qianmo.minepanel.DaemonConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

//todo Docker
@Component
@Slf4j
public class DockerManager {
    static private Boolean Enable;

    private DockerClient dockerClient;

    private final DaemonConfiguration daemonConfiguration;

    public DockerManager(DaemonConfiguration daemonConfiguration) {
        this.daemonConfiguration = daemonConfiguration;
    }

    public void Init() throws Exception{
        log.info("Try to connect docker...");
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(daemonConfiguration.getDocker())
                .build();
        dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).build();
        dockerClient.pingCmd().exec();
        Info info = dockerClient.infoCmd().exec();
        log.info("Successfully connected to docker!");
        log.info("Docker Version: " + info.getServerVersion());
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

    public static Boolean getEnable() {
        return Enable;
    }

    public static void setEnable(Boolean enable) {
        Enable = enable;
    }
}
