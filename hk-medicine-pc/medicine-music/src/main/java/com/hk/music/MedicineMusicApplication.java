package com.hk.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author pengzhengfa
 */
@ComponentScan(basePackages = {
        "com.hk.*"
})
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
public class MedicineMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineMusicApplication.class, args);
    }
}