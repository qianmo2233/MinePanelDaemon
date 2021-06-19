package com.qianmo.minepanel.Config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(destroyMethod = "", name = "EmbeddedDataSource")
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:config/data.db");
        dataSourceBuilder.type(SQLiteDataSource.class);
        return dataSourceBuilder.build();
    }
}
