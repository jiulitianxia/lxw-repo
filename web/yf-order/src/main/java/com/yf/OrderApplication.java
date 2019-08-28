package com.yf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrderApplication{
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	@Bean
	public IRule  myRule(){//负载策略
		 return new RandomRule();
	}
    public static void main( String[] args )
    {   
       SpringApplication.run(OrderApplication.class, args);
    }
}
