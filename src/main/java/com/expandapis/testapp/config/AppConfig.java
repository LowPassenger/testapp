package com.expandapis.testapp.config;

import com.expandapis.testapp.util.TableNameHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public TableNameHolder tableNameHolder() {
        return new TableNameHolder();
    }
}
