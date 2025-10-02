package com.codenlog.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;


@SpringBootApplication
@ComponentScan("com.codenlog")
public class MemberApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MemberApplication.class, args);
        ConfigurableEnvironment env = ctx.getEnvironment();

        LOGGER.info("启动成功~~");
        LOGGER.info("地址: \thttp://127.0.0.1:{}{}", env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));

    }

}
