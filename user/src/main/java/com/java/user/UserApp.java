package com.java.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jiangli
 * @date 2019/8/27 21:09
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.java.common","com.java.user"})
public class UserApp {
	public static void main(String[] args) {
		SpringApplication.run(UserApp.class,args);
	}
}
