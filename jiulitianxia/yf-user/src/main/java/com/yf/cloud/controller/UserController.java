package com.yf.cloud.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yf.cloud.common.CommonTools;
import com.yf.cloud.common.FastJson;
import com.yf.cloud.ibusiness.IUser;
import com.yf.cloud.info.UserApply;
import com.yf.cloud.info.UserInfo;
import com.yf.cloud.info.UserTable;
import com.yf.cloud.secret.Md5Tools;

@RestController
public class UserController {
	protected static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IUser iUser;
	/**
	 * 注册
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/register")
	public String register(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> param = new HashMap<String, Object>();
	   String username=request.getParameter("username");
	   String password =request.getParameter("password");
	   String repassword =request.getParameter("repassword");
	   logger.info("##username="+username+"##password="+password+"##repassword="+repassword);
	   if(!CommonTools.validParam(username,password,repassword)){
		 return FastJson.getFalse("0901", " 参数传递错误");
	   }
	   try {
		   UserTable usertable = new UserTable();
		   param.put("username", username);
		   int count = iUser.getUserById(param);
		   if(count>0){
			   return FastJson.getFalse("0103", "不能重复注册！");
		   }
		   usertable.setPassword(Md5Tools.getEncryptedPwd(password));
		   usertable.setRepassword(Md5Tools.getEncryptedPwd(repassword));
		   usertable.setUsername(username);
		   usertable.setName(username);
		   int id =iUser.saveUserInfo(usertable);
		   if(id>0){
			   System.out.println("保存成功id="+id);
		   }
		return FastJson.getTrueForWeb();
	  } catch (Exception e) {
		e.printStackTrace();
	 	return FastJson.getFalse("0100", "注册失败");
      }
	}
	
	/**
	 * 登录
	 */
	@PostMapping(value="/api/login")
	public String login(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> param = new HashMap<String, Object>();
	   String username=request.getParameter("username");
	   String password =request.getParameter("password");
	   logger.info("##username="+username+","+"##password="+password);
	   if(!CommonTools.validParam(username,password)){
		 return FastJson.getFalse("0901", " 参数传递错误");
	   }
	   try {
		   param.put("username", username);
		   UserTable usertable =iUser.getUserTableLoginInfo(param);
		   if(null ==usertable){
			   return FastJson.getFalse("0101", "用户不存在");
		   }else {
		     if(Md5Tools.validPassword(password, usertable.getPassword())) {
		    	 map.put("id", usertable.getId()+"");
			     map.put("status","true");
			     mapper.put("result", map);
			     return FastJson.getResultFromMap(mapper);
		      }else{
		    	  return FastJson.getFalse("0102", "密码错误");
		      }
		   }
	  } catch (Exception e) {
		e.printStackTrace();
	 	return FastJson.getFalse("0100", "登录失败");
      }
	}
	/**
	 * 添加用户
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/addUsers")
	public String addUsers(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   String userid =request.getParameter("uid");
	   String phone = request.getParameter("phone");
	   String money =request.getParameter("money");
	   String account =request.getParameter("account");
	   logger.info("########进入####/api/addUsers#######phone="+phone+"###money="+money+"account="+account+"##userid="+userid);
	   if(!CommonTools.validParam(phone,money,account,userid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
		   UserInfo userInfo  = new UserInfo();
		   userInfo.setName(phone);
		   userInfo.setPhone(phone);
		   userInfo.setMoney(Integer.parseInt(money));
		   userInfo.setAccountNum(Integer.parseInt(account));
		   userInfo.setUid(Integer.parseInt(userid));
	       int id =iUser.addUserInfo(userInfo);
	       if(id>0){
    	    map.put("status","true");
		    mapper.put("result", map);
		    return FastJson.getResultFromMap(mapper);
	       }
	   } catch (Exception e) {
		e.printStackTrace();
		return FastJson.getFalse("0200", "添加失败");
       }
	    map.put("status","true");
	    mapper.put("result", map);
	    return FastJson.getResultFromMap(mapper);
	}
	
	/**
	 * 存储使用次数
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/userapply")
	public String userapply(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> param = new HashMap<String, Object>();
	   String userid =request.getParameter("uid");
	   String phone = request.getParameter("phone");
	   String account =request.getParameter("account");
	   logger.info("########进入####/api/addUsers#######phone="+phone+"account="+account+"##userid="+userid);
	   if(!CommonTools.validParam(phone,account,userid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
		   UserApply userApply  = new UserApply();
		   userApply.setPhone(phone);
		   userApply.setAccountNum(Integer.parseInt(account));
		   userApply.setUid(Integer.parseInt(userid));
	       int id =iUser.addUserApplyTable(userApply);//存储用户使用情况
	       if(id>0){
	    	param.put("phone", phone);
	    	param.put("account", Integer.parseInt(account));
	    	iUser.updateUserInfoTable(param);//更新useinfo
    	    map.put("status","true");
		    mapper.put("result", map);
		    return FastJson.getResultFromMap(mapper);
	       }
	   } catch (Exception e) {
		e.printStackTrace();
		return FastJson.getFalse("0200", "添加失败");
       }
	    map.put("status","true");
	    mapper.put("result", map);
	    return FastJson.getResultFromMap(mapper);
	}
	/**
	 * 用户充值;
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/investmoney")
	public String investmoney(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   String uid =request.getParameter("uid");
	   String phone = request.getParameter("phone");
	   String money =request.getParameter("money");
	   String account =request.getParameter("account");
	   logger.info("########进入####/api/investmoney#######phone="+phone+"###money="+money+"account="+account+"##uid="+uid);
	   if(!CommonTools.validParam(phone,money,account,uid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
		   UserInfo userInfo  = new UserInfo();
		   userInfo.setName(phone);
		   userInfo.setPhone(phone);
		   userInfo.setMoney(Integer.parseInt(money));
		   userInfo.setAccountNum(Integer.parseInt(account));
	       iUser.updateInvestMoneyUserInfo(userInfo);
	       map.put("status","true");
	       mapper.put("result", map);
	       return FastJson.getResultFromMap(mapper);	  
        } catch (Exception e) {
		 e.printStackTrace();
		 return FastJson.getFalse("0200", "添加失败");
       }
	}
	/**
	 * 获取个人用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/getUserInfoByAccount")
	public String getUserInfoByAccount(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> param = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> result = new HashMap<String, Object>();
	   String phone = request.getParameter("phone");
	   String uid =request.getParameter("uid");
	   logger.info("########进入####/api/getUserInfoByAccount#######phone="+phone+"##uid="+uid);
	   if(!CommonTools.validParam(phone,uid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
	     param.put("phone", phone);
	     param.put("uid",Integer.parseInt(uid));
	     UserInfo userinfo =iUser.getUserInfoByAccount(param);
	     if(null !=userinfo){
	    	 map.put("personphone", userinfo.getPhone());
	    	 map.put("money", userinfo.getMoney()+"");
	    	 map.put("account", userinfo.getAccountNum()+"");
	    	 map.put("username", userinfo.getName()+"");
	     }else{
	    	 return FastJson.getFalse("0201", "无此用户信息！");
	     }
	     map.put("status","true");
	     result.put("data", map);
        return FastJson.getResultFromMap(result);	 
	  } catch (Exception e) {
		e.printStackTrace();
		 return FastJson.getFalse("0200", "获取用户信息失败！");
      }
	}
	
	
	/**
	 * 获取个人用户使用情况列表信息
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/getUserApplyByList")
	public String getUserApplyByList(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> param = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> result = new HashMap<String, Object>();
	   SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
	   String phone = request.getParameter("phone");
	   String uid =request.getParameter("uid");
	   logger.info("########进入####/api/getUserApplyByList#######phone="+phone+"##uid="+uid);
	   if(!CommonTools.validParam(phone,uid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
	     param.put("phone", phone);
	     param.put("uid",Integer.parseInt(uid));
	     List<UserApply> list =iUser.getUserApplyByList(param);
	     if(list.size()>0){
	    	 for(int i=0;i<list.size();i++){
	    		 Map<String, Object> retmap = new HashMap<String, Object>();
	    		 retmap.put("phone", list.get(i).getPhone());
	    		 retmap.put("accounttimes", list.get(i).getAccountNum()+"");
	    		 retmap.put("time", format.format(list.get(i).getUpdatetime()));
	    		 retList.add(retmap);
	    	 }
	     }
	     map.put("dataList", retList);
	     map.put("status","true");
	     result.put("result", map);
        return FastJson.getResultFromMap(result);	 
	  } catch (Exception e) {
		e.printStackTrace();
		 return FastJson.getFalse("0200", "获取用户信息失败！");
      }
	}
	/**
	 * 获取所有用户充值情况
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/getAllUserInfoByList")
	public String getAllUserInfoByList(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> param = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> result = new HashMap<String, Object>();
	   SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
	   String uid =request.getParameter("uid");
	   logger.info("########进入####/api/getUserApplyByList########uid="+uid);
	   if(!CommonTools.validParam(uid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
	     param.put("uid",Integer.parseInt(uid));
	     List<UserInfo> list =iUser.getAllUserInfoByList(param);
	     if(list.size()>0){
	    	 for(int i=0;i<list.size();i++){
	    		 Map<String, Object> retmap = new HashMap<String, Object>();
	    		 retmap.put("phone", list.get(i).getPhone());
	    		 retmap.put("money", list.get(i).getMoney()+"");
	    		 retmap.put("accounttimes", list.get(i).getAccountNum()+"");
	    		 retmap.put("time", format.format(list.get(i).getCreatetime()));
	    		 retList.add(retmap);
	    	 }
	     }
	     map.put("dataList", retList);
	     map.put("status","true");
	     result.put("result", map);
        return FastJson.getResultFromMap(result);	 
	  } catch (Exception e) {
		e.printStackTrace();
		 return FastJson.getFalse("0200", "获取用户信息失败！");
      }
	}
}
