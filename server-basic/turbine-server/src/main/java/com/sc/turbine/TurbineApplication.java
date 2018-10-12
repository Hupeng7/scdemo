package com.sc.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * @author hp
 * @version 1.0
 * @description: ${description}
 * @date: 14:42 2018/10/12 0012
 */
@SpringBootApplication
@EnableTurbineStream
public class TurbineApplication {
    public static void main(String[] args) {
        SpringApplication.run(TurbineApplication.class);
    }
}
