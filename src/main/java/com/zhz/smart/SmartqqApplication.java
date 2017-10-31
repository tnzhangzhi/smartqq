package com.zhz.smart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@EnableConfigurationProperties
@MapperScan(basePackages = "com.zhz.smart.mapper")
@SpringBootApplication
public class SmartqqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartqqApplication.class, args);
	}
}
