package com.emc.belustyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BeluStyleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeluStyleApplication.class, args);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
//		System.out.println(encoder.encode("password12345"));
    }
}
