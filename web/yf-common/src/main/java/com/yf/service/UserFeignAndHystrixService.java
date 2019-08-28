package com.yf.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.yf.info.UserTable;

@FeignClient(name = "yf-user" , fallbackFactory  = UserFeignClientFactory.class)
public interface UserFeignAndHystrixService {
	/**
	 * feign 与Hystrix
	 * @param id
	 * @return
	 */
	 @RequestMapping(method = RequestMethod.POST, value="/api/getMyUserById/{id}")
	 public UserTable getFeignOrderById(@PathVariable("id") String id);

	@RequestMapping(method = RequestMethod.POST, value="/api/getPassword/{username}")
	public String getPassword(@PathVariable("username") String username);

	@RequestMapping(method = RequestMethod.POST, value="/api/getPassword/{username}")
	public int saveUserInfo(UserTable usertable);
	
}
