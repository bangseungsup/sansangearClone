package com.clone.sansansgear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SansansgearApplication {

    public static void main(String[] args) {
        SpringApplication.run(SansansgearApplication.class, args);
    }

}
