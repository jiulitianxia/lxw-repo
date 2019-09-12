package com.yf.cloud.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/yf")
@Scope(value = "prototype")
public class ViewController {
	protected static final Logger logger = LoggerFactory.getLogger(ViewController.class);
	/**
	 * 进入登录页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request){
		 logger.info("################进入/yf/index##############");
		 return new ModelAndView("login");
	}
	/**
	 * 跳转用户列表
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/home")
	public ModelAndView home(HttpServletRequest request){
		 logger.info("################进入/api/home##############");
		 return new ModelAndView("home");
	}
	/**
	 * 跳转添加用户页
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/addUserInfo")
	public ModelAndView adduser(HttpServletRequest request){
		 logger.info("################进入/api/addUserInfo##############");
		 return new ModelAndView("adduser");
	}
	/**
	 * 跳转使用详情页
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getUserDetail")
	public ModelAndView getUserDetail(HttpServletRequest request){
		 logger.info("################进入/api/getUserDetail##############");
		 return new ModelAndView("userApplyDetail");
	}
	/**
	 * 跳转用户信息编辑页
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/editUserInfo")
	public ModelAndView editUserInfo(HttpServletRequest request){
		 logger.info("################进入/yf/editUserInfo##############");
		 return new ModelAndView("editusers");
	}
	/**
	 * 跳转用户使用
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/userapply")
	public ModelAndView userapply(HttpServletRequest request){
		 logger.info("################进入/yf/userapply##############");
		 return new ModelAndView("userapply");
	}
}
