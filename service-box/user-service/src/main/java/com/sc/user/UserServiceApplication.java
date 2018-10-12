package com.sc.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * 开启feign客户端远程调用
 */
@EnableFeignClients
@SpringCloudApplication
@EnableHystrix
/**
 * 开启自定义配置属性扫描
 */
@EnableConfigurationProperties
/**
 * 托管类扫描路径
 */
@ComponentScan(basePackages = {"com.sc.common", "com.sc.user"})
/**
 * @author hp
 * @version 1.0
 * @description: ${description}
 * @date: 18:27 2018/10/11 0011
 */
public class UserServiceApplication {
    /**
     * 注入可以手动设定固定网址（如第三方登录、第三方支付）的httpClient对象
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class);
    }
}
