package com.qianmo.minepanel.Docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.MinePanelApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

//todo Docker
@Component
public class DockerManager {
    private DockerClient dockerClient;

    @Autowired
    private DaemonConfiguration daemonConfiguration;

    public void Init() throws Exception{
        MinePanelApplication.getLogger().info("Try to connect docker...");
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(daemonConfiguration.getDocker())
                .build();
        dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).build();
        Info info = dockerClient.infoCmd().exec();
        MinePanelApplication.getLogger().info("Successfully connected to docker!");
        MinePanelApplication.getLogger().info("Docker Version: " + info.getServerVersion());
    }

    public List<SearchItem> searchImages(String keyword) {
        return dockerClient.searchImagesCmd(keyword).exec();
    }

    public void pullImages(String image) {
        dockerClient.pullImageCmd(image).exec(new ResultCallback<PullResponseItem>() {
            @Override
            public void onStart(Closeable closeable) {
                //
            }

            @Override
            public void onNext(PullResponseItem pullResponseItem) {
                //
            }

            @Override
            public void onError(Throwable throwable) {
                //
            }

            @Override
            public void onComplete() {
                //
            }

            @Override
            public void close() throws IOException {
                //
            }
        });
    }

    public void create(String image, String name, Long memory, Integer cpu, Integer port) {
        Ports portBindings = new Ports();
        portBindings.bind(ExposedPort.tcp(port), Ports.Binding.bindPort(port));
        CreateContainerResponse container = dockerClient.createContainerCmd(image)
                .withName(name)
                .withMemory(memory*1024*1024)
                .withCpuShares(cpu)
                .withExposedPorts(ExposedPort.tcp(port))
                .withHostConfig(newHostConfig().withPortBindings(portBindings))
                .exec();
    }
}
