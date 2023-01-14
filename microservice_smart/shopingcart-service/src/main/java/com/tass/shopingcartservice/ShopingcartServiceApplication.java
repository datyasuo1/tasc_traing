package com.tass.shopingcartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShopingcartServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShopingcartServiceApplication.class, args);
    }

}
