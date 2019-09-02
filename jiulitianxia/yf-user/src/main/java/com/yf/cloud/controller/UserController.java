package com.yf.cloud.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yf.cloud.common.CommonTools;
import com.yf.cloud.common.FastJson;
import com.yf.cloud.ibusiness.IUser;
import com.yf.cloud.info.UserTable;
import com.yf.cloud.secret.Md5Tools;

@RestController
@Component
public class UserController {
	protected static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
    private IUser iUser;
	@Value("${spring.username}")
	private String newusername;
	
	@Value("${spring.password}")
	private String newpassword;
	
	
	@PostMapping(value="/api/register")
	public String register(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> map = new HashMap<String, Object>();
	   String username=request.getParameter("username");
	   String password =request.getParameter("password");
	   System.out.println("##username="+username+"##password="+password);
	   if(!CommonTools.validParam(username,password)){
		 return FastJson.getFalse("0901", " 参数传递错误");
	   }
	   try {
		   UserTable usertable = new UserTable();
		   usertable.setPassword(Md5Tools.getEncryptedPwd(password));
		   usertable.setUsername(username);
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
	
	/**
	 * 登陆
	 */
	@PostMapping(value="/api/login")
	public String login(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> param = new HashMap<String, Object>();
	   String username=request.getParameter("username");
	   String password =request.getParameter("password");
	   System.out.println("##username="+username+","+"##password="+password);
	   if(!CommonTools.validParam(username,password)){
		 return FastJson.getFalse("0901", " 参数传递错误");
	   }
	   try {
		   param.put("username", username);
		   UserTable usertable =iUser.getUserTableLoginInfo(param);
		   if(null ==usertable){
			   return FastJson.getFalse("0101", "用户不存在或者用户名密码错误");
		   }else {
			   if(!usertable.getUsername().equals(newusername)&&!usertable.getPassword().equals(newpassword)) {
				   return FastJson.getFalse("0101", "用户不存在或者用户名密码错误");
			   }
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
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/getUserById/{id}")
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
}
