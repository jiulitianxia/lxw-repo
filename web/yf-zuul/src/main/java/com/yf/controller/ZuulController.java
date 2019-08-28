package com.yf.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yf.common.CommonTools;
import com.yf.common.FastJson;
import com.yf.info.UserTable;
import com.yf.service.UserFeignAndHystrixService;

@RestController
public class ZuulController {
	@Autowired
	private UserFeignAndHystrixService userFeignAndHystrixService;
	
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
		   int id =userFeignAndHystrixService.saveUserInfo(usertable);
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
		   String realPassword = userFeignAndHystrixService.getPassword(username);
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
}
