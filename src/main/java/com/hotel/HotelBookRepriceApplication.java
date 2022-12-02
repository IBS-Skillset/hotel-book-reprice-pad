package com.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HotelBookRepriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelBookRepriceApplication.class, args);}
}
