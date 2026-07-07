package org.example.ingtest;

import org.example.ingtest.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class IngTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngTestApplication.class, args);
    }

}
