package com.qianmo.minepanel;

import com.qianmo.minepanel.Loader.Initializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@ServletComponentScan(value = "com.qianmo.minepanel.Loader.ContextListener")
@EnableConfigurationProperties
public class MinePanelDaemon {

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
                System.exit(1);
            }
        }
        SpringApplication.run(MinePanelDaemon.class, args);
    }
}
