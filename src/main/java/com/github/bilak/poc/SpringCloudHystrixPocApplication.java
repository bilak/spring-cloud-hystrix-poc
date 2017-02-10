package com.github.bilak.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
@EnableFeignClients
public class SpringCloudHystrixPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudHystrixPocApplication.class, args);
	}
}
