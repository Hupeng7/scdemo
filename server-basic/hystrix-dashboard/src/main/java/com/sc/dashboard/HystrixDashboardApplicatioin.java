package com.sc.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author hp
 * @version 1.0
 * @description: 监控平台
 * @date: 14:00 2018/10/12 0012
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApplicatioin {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplicatioin.class);

    }
}
