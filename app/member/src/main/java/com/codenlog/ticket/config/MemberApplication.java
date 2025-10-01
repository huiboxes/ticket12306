package com.codenlog.ticket.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.codenlog")
public class MemberApplication {

    public static void main(String[] args) {

        SpringApplication.run(MemberApplication.class, args);
    }

}
