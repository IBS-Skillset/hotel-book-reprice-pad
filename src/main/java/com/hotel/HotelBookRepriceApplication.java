package com.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hotel.util.ErrorMappings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
@EnableEurekaClient
public class HotelBookRepriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelBookRepriceApplication.class, args);}

    @Bean
    public ErrorMappings errorMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(new ClassPathResource("/error.yaml")
                        .getInputStream(),
                ErrorMappings.class);

    }
}
