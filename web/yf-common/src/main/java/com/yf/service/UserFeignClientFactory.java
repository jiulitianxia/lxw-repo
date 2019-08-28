package com.yf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yf.info.UserTable;

import feign.hystrix.FallbackFactory;
@Component
public class UserFeignClientFactory  implements FallbackFactory<UserFeignAndHystrixService>{
	private final static Logger logger= LoggerFactory.getLogger(UserFeignClientFactory.class);
	@Override
	public UserFeignAndHystrixService create(Throwable cause) {
		
		return new UserFeignAndHystrixService(){

			@Override
			public UserTable getFeignOrderById(String id) {
				logger.error("###########################xiaowei",cause);
				UserTable user =new UserTable();
				user.setId(Integer.parseInt(id));
				user.setName("该ID:"+id+"数据不存在，或者服务降级，已关闭！");
				return  user;
			}

			@Override
			public String getPassword(String username) {
				return null;
			}

			@Override
			public int saveUserInfo(UserTable usertable) {
				return 0;
			}
		};
	}
	

}
