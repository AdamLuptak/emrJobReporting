package com.here.owc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.here.owc")
public class ApplicationConfiguration {

    @Bean
    public String helloWorld(){
        return "Trigerol mal sns";
    }

}
