package com.java.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author jiangli
 * @date 2019/8/21 19:04
 */
@SpringBootApplication
@EnableConfigServer
@ServletComponentScan(value = "com.java.config.busConfig")  //扫描@WebFilter注解所在的包(自动刷新配置用)
public class ConfigApp {
	public static void main(String[] args) {
		SpringApplication.run(ConfigApp.class,args);
	}
}
