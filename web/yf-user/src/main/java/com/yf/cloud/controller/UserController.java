package com.yf.cloud.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yf.cloud.ibusiness.IUser;
import com.yf.common.CommonTools;
import com.yf.common.FastJson;
import com.yf.info.UserTable;

@RestController
@Component
public class UserController {
	protected static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
    private IUser iUser;
	
	@PostMapping(value="/api/register")
	public String register(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> map = new HashMap<String, Object>();
	   String name=request.getParameter("name");
	   String username=request.getParameter("username");
	   String phone =request.getParameter("phone");
	   String age =request.getParameter("age");
	   String sex =request.getParameter("sex");//0:男，1：女
	   String password =request.getParameter("password");
	   System.out.println("##name="+name+","+"##username="+username+","+"##phone="+phone
			   +","+"##age="+age+","+"##sex="+sex+","+"##password="+password);
	   if(!CommonTools.validParam(username,age,sex,password)){
		 return FastJson.getFalse("0901", " 参数传递错误");
	   }
	   try {
		   UserTable usertable = new UserTable();
		   usertable.setName(name);
		   usertable.setPassword(password);
		   usertable.setUsername(username);
		   usertable.setAge(Integer.parseInt(age));
		   usertable.setPhone(phone);
		   usertable.setSex(Integer.parseInt(sex));
		   int id =iUser.saveUserInfo(usertable);
		   if(id>0){
			   System.out.println("保存成功id="+id);
		   }
		   map.put("status","true");
		   map.put("id", String.valueOf(id));
		return FastJson.getResultFromMap(map);
	  } catch (Exception e) {
		e.printStackTrace();
	 	return FastJson.getFalse("0100", "注册失败");
      }
	}
	@PostMapping(value="/api/login")
	public String login(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   String username=request.getParameter("username");
	   String password =request.getParameter("password");
	   System.out.println("##username="+username+","+"##password="+password);
	   if(!CommonTools.validParam(username,password)){
		 return FastJson.getFalse("0901", " 参数传递错误");
	   }
	   try {
		   String realPassword = iUser.getPassword(username);
		   if (realPassword == null) {
			   return FastJson.getFalse("0101", "用户名错误");
		    } else if (!realPassword.equals(password)) {
		    	 return FastJson.getFalse("0102", "密码错误");
		    } else {
		    	// 从SecurityUtils里边创建一个 subject
		        Subject subject = SecurityUtils.getSubject();
		        // 在认证提交前准备 token（令牌）
		        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		        // 执行认证登陆
		        subject.login(token);
		    }
		   map.put("status","true");
		   mapper.put("result", map);
		  return FastJson.getResultFromMap(mapper);
	  } catch (Exception e) {
		e.printStackTrace();
	 	return FastJson.getFalse("0100", "登录失败");
      }
	}
	/**
	 * 获取个人用户信息
	 * @@@@@@@不建议使用
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/getUserById/{id}")
	@HystrixCommand(fallbackMethod="getHysFallBack")
	public UserTable getUserById(@PathVariable String id){
	   Map<String, Object> param = new HashMap<String, Object>();
	   logger.info("########进入####/api/getUserById#######id="+id);
	   try {
	     param.put("id", Integer.parseInt(id));
	     UserTable usertable =iUser.getUserById(param);
	     Date times =usertable.getCreatetime();
	     logger.info("@@@@times="+times);
		 return usertable;
	  } catch (Exception e) {
		logger.error(e.getCause().toString());
		e.printStackTrace();
	 	return new UserTable() ;
      }
	}
	
	/**
	 * @param id
	 * @return
	 */
	public  UserTable  getHysFallBack(String id){
		logger.debug("################getFallBack#########id="+id);
		UserTable user = new UserTable();
		user.setId(Integer.parseInt(id));
		user.setName("该ID:"+id+"没有对应的信息 null --@HystrixCommand");
		return user;
	}
	
	/**
	 * 获取个人用户信息
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/getMyUserById/{id}")
	public UserTable getMyUserById(@PathVariable String id,HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> param = new HashMap<String, Object>();
	   logger.info("########进入####/api/getMyUserById#######id="+id);
	   try {
	     param.put("id", Integer.parseInt(id));
	     UserTable usertable =iUser.getUserById(param);
	     Date times =usertable.getCreatetime();
	     logger.info("@@@@times="+times);
		 return usertable;
	  } catch (Exception e) {
		logger.error(e.getCause().toString());
		e.printStackTrace();
	 	return new UserTable() ;
      }
	}
	
	
	
	/**
	 * 获取所有用户
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value="/api/getAllUser")
	public List<UserTable> getAllUser(HttpServletRequest request,HttpServletResponse response){
	   try {
		 logger.debug("@@@@@@@@@@getAllUser@@@@@@@@@@@@");
	     List<UserTable> listuser =iUser.getAllUserInfo();
		 return listuser;
	  } catch (Exception e) {
		e.printStackTrace();
	 	return new ArrayList<UserTable>();
      }
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/getPassword/{username}")
	public String getPassword(@PathVariable String username){
	   logger.info("########进入####/api/getPassword#######username="+username);
	   try {
		 String realPassword = iUser.getPassword(username);
		 return realPassword;
	  } catch (Exception e) {
		logger.error(e.getCause().toString());
		e.printStackTrace();
	 	return null;
      }
	}
}
