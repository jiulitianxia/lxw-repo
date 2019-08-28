package com.yf.cloud.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.yf.common.CustomException;
import com.yf.common.CustomResponseErrorHandler;
import com.yf.info.UserTable;
import com.yf.service.UserFeignAndHystrixService;
import com.yf.service.UserServiceFeignClient;

@RestController
public class OrderController {
	protected static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private  RestTemplate restTemplate;
	@Autowired
	private UserServiceFeignClient userServiceFeignClient;
	
	@Autowired
	private UserFeignAndHystrixService userFeignAndHystrixService;
	
	/**
	 * ribbon restTemplate
	 * url就是请求的地址，responseType返回的数据类型，uriVariables为url中参数绑定
	 * @param id
	 * @return
	 */
    @GetMapping(value="/api/getOrderById/{id}")
	public String getOrderById(@PathVariable String id){
		logger.info("#########进入/api/getOrderById#########id="+id);
		// 错误处理
	       restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		  try {
			ResponseEntity<Object> res = restTemplate.postForEntity("http://yf-user/api/getUserById/"+id,"",Object.class);
			
			String jsonStr = JSON.toJSONString(res.getBody());
			
			return jsonStr;
		} catch (CustomException e) {//CustomException
			logger.error("代码出现错误："+e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * feign与Hystrix
	 * @param id
	 * @return
	 */
	@GetMapping(value="/order/getFeignOrderById/{id}")
	public String getFeignOrderById(@PathVariable String id){
		logger.info("#########进入/api/getFeignOrderById#########id="+id);
		  try {
			UserTable jsonUser =userFeignAndHystrixService.getFeignOrderById(id);
			String userJsonStr = JSON.toJSONString(jsonUser);
			return userJsonStr;
		} catch (Exception e) {
			logger.error("代码出现错误："+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * feign加Hystrix
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value="/order/getFHystrixOrderById/{id}")
	public String getFOrderById(@PathVariable String id){
		logger.info("#########进入/api/getFHystrixOrderById#########id="+id);
		  try {
			
			UserTable jsonUser =userServiceFeignClient.getFHystrixOrderById(id);
			String userJsonStr = JSON.toJSONString(jsonUser);
			return userJsonStr;
		} catch (Exception e) {
			logger.error("代码出现错误："+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * lxw  获取List用数组接收否则会报错
	 * @return
	 */
	@GetMapping(value="/order/AllUserorder")
	public  List<UserTable> AllUserorder(){
      try {
	   logger.debug("#####AllUserorder");
	   UserTable[] usert =restTemplate.postForObject("http://yf-user/api/getAllUser","", UserTable[].class, "");
	   /*UserTable[] ut = restTemplate.getForObject("http://yf-user/api/getAllUser", UserTable[].class);//获取List用数组接收否则会报错*/	   
	   List<UserTable> list = Arrays.asList(usert);//数组转List
	   for(UserTable u:list){
		  logger.debug(u.getName());
	   }
	   return list;
	 } catch (Exception e) {
		e.printStackTrace();
		 return new ArrayList<UserTable>();
	}
 }
	
}
