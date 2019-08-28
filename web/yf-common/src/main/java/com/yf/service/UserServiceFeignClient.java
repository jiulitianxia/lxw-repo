package com.yf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yf.info.UserTable;
@FeignClient(name = "yf-user" , fallback = UserServiceFeignClient.HystrixClientFallback.class)
public interface UserServiceFeignClient {
	/**
	 * Hystrix
	 * @param id
	 * @return
	 */
	/*使用@RequestParam时，URL是这样的：http://host:port/path?参数名=参数值
	 * 使用@PathVariable时，URL是这样的：http://host:port/path/参数值*/
	
	@RequestMapping(method = RequestMethod.POST, value="/api/getMyUserById/{id}")
	public UserTable getFHystrixOrderById(@PathVariable("id") String id);
	
	@Component
	static class HystrixClientFallback implements UserServiceFeignClient {
		private final static Logger logger= LoggerFactory.getLogger(HystrixClientFallback.class);
	    @Override
	    public UserTable getFHystrixOrderById(@PathVariable("id") String id) {
	    	logger.info("#xiaowei####getFHystrixOrderById#####fallback");
	    	UserTable user = new UserTable();
	    	user.setId(Integer.parseInt(id));
	        return user;
	    }
	}
}
