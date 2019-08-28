package com.yf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.yf.filter.PreFilter;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableZuulProxy
public class ZuulApplication {
	
	@Bean
    public PreFilter preFilter(){
        return new PreFilter();
    }
	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

}
