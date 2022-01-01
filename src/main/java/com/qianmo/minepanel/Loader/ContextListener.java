package com.qianmo.minepanel.Loader;

import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Docker.DockerManager;
import com.qianmo.minepanel.Server.FTP.FTPServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Component
@Slf4j
public class ContextListener implements ServletContextListener, ApplicationListener<WebServerInitializedEvent> {

    private final FTPServer ftpServer;

    private final DockerManager dockerManager;

    private final DaemonConfiguration daemonConfiguration;

    public ContextListener(FTPServer ftpServer, DockerManager dockerManager, DaemonConfiguration daemonConfiguration) {
        this.ftpServer = ftpServer;
        this.dockerManager = dockerManager;
        this.daemonConfiguration = daemonConfiguration;
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ftpServer.Stop();
        sce.getServletContext().removeAttribute("FTP-SERVER");
        log.info("Daemon is stopped!");
    }

    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("FTP-SERVER",ftpServer);
        try {
            ftpServer.Start();
            log.info("FTP server is started!");
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("FTP server start failed!", e);
        }
        if(!(daemonConfiguration.getDocker().equals("") || daemonConfiguration.getDocker().equals("none"))) {
            try {
                dockerManager.Init();
            } catch (Exception e) {
                log.error("Failed to connect docker:" + e);
                DockerManager.setEnable(false);
                log.warn("The virtualization container will not be enabled");
            }
        } else {
            DockerManager.setEnable(false);
            log.warn("Docker is disabled,the virtualization container will not be enabled");
        }
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        log.info("Daemon runs on port " + webServerInitializedEvent.getWebServer().getPort());
    }
}
