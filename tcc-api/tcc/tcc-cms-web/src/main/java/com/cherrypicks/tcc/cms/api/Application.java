package com.cherrypicks.tcc.cms.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cherrypicks.tcc.cms.api.config.DataSourceConfig;
import com.cherrypicks.tcc.cms.api.config.RedisConfig;
import com.cherrypicks.tcc.cms.api.config.WebConfig;

@SpringBootApplication
@EnableScheduling
@Import({WebConfig.class, DataSourceConfig.class, RedisConfig.class})
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(final String[] args) {
        final SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
}
