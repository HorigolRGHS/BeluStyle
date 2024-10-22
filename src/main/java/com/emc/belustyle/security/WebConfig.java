package com.emc.belustyle.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        // Ignore all requests starting with /api or /auth
//        registry.addViewController("/{spring:\\w+}")
//                .setViewName("forward:/index.html");
//        registry.addViewController("/**/{spring:\\w+}")
//                .setViewName("forward:/index.html");
//        registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css|\\.png)$}")
//                .setViewName("forward:/index.html");
//    }
}


