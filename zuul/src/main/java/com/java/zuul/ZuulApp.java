package com.java.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jiangli
 * @date 2019/8/26 23:00
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@ComponentScan(basePackages = {"com.java.common","com.java.zuul"})
public class ZuulApp {
	public static void main(String[] args) {
		SpringApplication.run(ZuulApp.class,args);
	}

	//动态路由
	@ConfigurationProperties("zuul")
	@RefreshScope
	public ZuulProperties zuulProperties() {
		return new ZuulProperties();
	}
}
