package com.sc.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author hp
 * @version 1.0
 * @description: 配置中心
 * 拉去远程仓库git/gitlab文件属性
 * 动态加载到本地 ${} 格式的属性上
 * @date: 13:47 2018/10/12 0012
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class);
    }
}
