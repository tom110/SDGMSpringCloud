package com.tom.authclientserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthClientServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthClientServerApplication.class, args);
    }
}
