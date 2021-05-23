package com.qianmo.minepanel;

import com.qianmo.minepanel.Server.FTP.FTPServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@WebListener
@Component
public class Initializer implements ServletContextListener, ApplicationListener<WebServerInitializedEvent> {
    private static final String SERVER_NAME="FTP-SERVER";
    @Autowired
    private FTPServer ftpServer;

    public void contextDestroyed(ServletContextEvent sce) {
        ftpServer.Stop();
        sce.getServletContext().removeAttribute(SERVER_NAME);
        MinePanelApplication.getLogger().info("FTP server is stopped!");
    }

    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(SERVER_NAME,ftpServer);
        try {
            ftpServer.Start();
            MinePanelApplication.getLogger().info("FTP server is started!");
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("FTP server start failed!", e);
        }
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        MinePanelApplication.getLogger().info("Daemon runs on port " + webServerInitializedEvent.getWebServer().getPort());
    }

    public static void Init() throws Exception {
        //System.out.println(new File("data/cores/spigot-1.16.2.jar").getAbsolutePath());
        File Config = new File("config/");
        File Server = new File("data/servers/");
        File Core = new File("data/cores/");
        File Template = new File("data/templates");
        File Data = new File("data/");
        File file = new File("config/application.yml");
        if(!Data.exists()) {
            Server.mkdirs();
            Core.mkdirs();
            Template.mkdirs();
        }
        if(!Config.exists()) {
            Config.mkdirs();
        }
        if(!file.exists()) {
            System.out.println("MinePanelDaemon需要配置文件(application.yml)才能运行!");
            System.out.println("请前往MinePanel控制面板生成配置文件");
            System.out.println("按回车继续");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.exit(2);
        }
    }
}
