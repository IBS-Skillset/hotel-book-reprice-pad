package com.hotel;

import com.hotel.config.RSAConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties({RSAConfiguration.class})
public class HotelBookRepriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelBookRepriceApplication.class, args);}
}
