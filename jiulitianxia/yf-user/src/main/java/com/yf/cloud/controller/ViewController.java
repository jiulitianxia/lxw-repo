package com.yf.cloud.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ViewController {
	protected static final Logger logger = LoggerFactory.getLogger(ViewController.class);
	/**
	 * 进入登录页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/view/login")
	public ModelAndView index(HttpServletRequest request){
		 logger.info("################进入/view/login##############");
		 return new ModelAndView("login");
	}
	/**
	 * 跳转首页
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/view/home")
	public ModelAndView home(HttpServletRequest request){
		 logger.info("################进入/api/home##############");
		 return new ModelAndView("home");
	}
	/**
	 * 跳转添加用户页
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/view/addUserInfo")
	public ModelAndView adduser(HttpServletRequest request){
		 logger.info("################进入/api/addUserInfo##############");
		 return new ModelAndView("adduser");
	}
}
