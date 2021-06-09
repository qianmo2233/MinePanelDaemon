package com.qianmo.minepanel;

import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("Mapper")
@EnableConfigurationProperties
public class MinePanelApplication {
    private static final Logger logger = LoggerFactory.getLogger("Daemon");

    public static void main(String[] args) {
        try {
            Initializer.Init();
        } catch (Exception e) {
            System.out.println("Daemon启动失败!");
            System.out.println("按回车继续");
            try {
                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.exit(1);
        }
        SpringApplication.run(MinePanelApplication.class, args);
    }

    public static Logger getLogger() {
        return logger;
    }
}
