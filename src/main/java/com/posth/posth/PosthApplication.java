package com.posth.posth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PosthApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosthApplication.class, args);
    }

}
