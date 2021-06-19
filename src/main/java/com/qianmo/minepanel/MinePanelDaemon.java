package com.qianmo.minepanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@ServletComponentScan(value = "com.qianmo.minepanel.Initializer")
@EnableConfigurationProperties
public class MinePanelDaemon {
    private static final Logger logger = LoggerFactory.getLogger("Daemon");

    public static void main(String[] args) {
        try {
            Initializer.Init();
        } catch (Exception e) {
            System.out.println("--------------------------");
            System.out.println("Daemon start failed!");
            System.out.println("--------------------------");
            e.printStackTrace();
            System.out.println("Press enter to continue");
            try {
                new BufferedReader(new InputStreamReader(System.in)).readLine();
                System.exit(1);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        SpringApplication.run(MinePanelDaemon.class, args);
    }

    public static Logger getLogger() {
        return logger;
    }
}
