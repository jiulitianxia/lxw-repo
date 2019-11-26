package com.yf.cloud.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yf.cloud.common.CommonTools;
import com.yf.cloud.common.FastJson;
import com.yf.cloud.ibusiness.IUser;
import com.yf.cloud.info.MoneySet;
import com.yf.cloud.info.UserApply;
import com.yf.cloud.info.UserInfo;
import com.yf.cloud.info.UserTable;
import com.yf.cloud.secret.AesUtils;

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
	@PostMapping(value="/register")
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
		   param.put("username", AesUtils.aesDecrypt(username));
		   int count = iUser.getUserById(param);
		   if(count>0){
			   return FastJson.getFalse("0103", "不能重复注册！");
		   }
		   usertable.setPassword(AesUtils.encryptString(AesUtils.aesDecrypt(password)));
		   usertable.setRepassword(AesUtils.encryptString(AesUtils.aesDecrypt(repassword)));
		   usertable.setUsername(AesUtils.aesDecrypt(username));
		   usertable.setName(AesUtils.aesDecrypt(username));
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
	@PostMapping(value="/login")
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
		   param.put("username", AesUtils.aesDecrypt(username));
		   UserTable usertable =iUser.getUserTableLoginInfo(param);
		   if(null ==usertable){
			   return FastJson.getFalse("0101", "用户不存在");
		   }else {
		     if(AesUtils.aesDecrypt(username).equals(usertable.getUsername())&&
				   AesUtils.encryptString(AesUtils.aesDecrypt(password)).equals(usertable.getPassword())) {
		    	UsernamePasswordToken token = new UsernamePasswordToken(AesUtils.aesDecrypt(username), usertable.getPassword());
			    Subject subject = SecurityUtils.getSubject();
			    try {
			    	subject.login(token);
			        map.put("id", usertable.getId()+"");
				    map.put("status","true");
				    mapper.put("result", map);
				    return FastJson.getResultFromMap(mapper);
			    } catch (Exception e) {
			    	return FastJson.getFalse("0100", "登录失败");
			    }
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
	 * 退出
	 */
	@PostMapping(value="/api/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
	   try {
		   Subject subject = SecurityUtils.getSubject();
		   if (subject.isAuthenticated()) {
			   subject.logout();
		   }
		   return FastJson.getOk();
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
	 * @throws Exception 
	 */
	@PostMapping(value="/api/addNewUserInfo")
	public String addNewUserInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> param = new HashMap<String, Object>();
	   String userid =request.getParameter("uid");
	   String phone = request.getParameter("phone");
	   String money =request.getParameter("money");
	   String account =request.getParameter("account");
	   String rewardacc =request.getParameter("rewardacc");
	   logger.info("########进入####/api/addNewUserInfo#######phone="+ AesUtils.aesDecrypt(phone)+
			   "###money="+money+"account="+account+"##userid="+userid+"##rewardacc="+rewardacc);
	   if(!CommonTools.validParam(phone,money,account,userid,rewardacc)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
		   param.put("uid", Integer.parseInt(userid));
		   param.put("phone", AesUtils.aesDecrypt(phone));
		   UserInfo ufo =iUser.getUserInfoByAccount(param);
		   if(null !=ufo){
			   return FastJson.getFalse("0205", "用户已经存在！");
		   }
		   UserInfo userInfo  = new UserInfo();
		   userInfo.setName(AesUtils.aesDecrypt(phone));
		   userInfo.setPhone(AesUtils.aesDecrypt(phone));
		   userInfo.setMoney(Integer.parseInt(money));
		   userInfo.setAccountNum(Integer.parseInt(account)+Integer.parseInt(rewardacc));
		   userInfo.setUid(Integer.parseInt(userid));
		   userInfo.setRewardaccount(Integer.parseInt(rewardacc));
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
	 * 编辑用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/api/editNewUserInfo")
	public String editNewUserInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   String userid =request.getParameter("uid");
	   String phone = request.getParameter("phone");
	   String money =request.getParameter("money");
	   String account =request.getParameter("account");
	   String rewardacc =request.getParameter("rewardacc");
	   logger.info("########进入####/api/editNewUserInfo#######phone="+AesUtils.aesDecrypt(phone)+"###money="+money+"account="+account+"##userid="+userid
			   +"##rewardacc="+rewardacc);
	   if(!CommonTools.validParam(phone,money,account,userid,rewardacc)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
		   UserInfo userInfo  = new UserInfo();
		   userInfo.setName(AesUtils.aesDecrypt(phone));
		   userInfo.setPhone(AesUtils.aesDecrypt(phone));
		   userInfo.setMoney(Integer.parseInt(money));
		   userInfo.setAccountNum(Integer.parseInt(account)+Integer.parseInt(rewardacc));
		   userInfo.setUid(Integer.parseInt(userid));
		   userInfo.setRewardaccount(Integer.parseInt(rewardacc));
	       iUser.updateUserInfo(userInfo);
    	   map.put("status","true");
		   mapper.put("result", map);
		   return FastJson.getResultFromMap(mapper);
	   } catch (Exception e) {
		e.printStackTrace();
		return FastJson.getFalse("0200", "编辑失败");
       }
	}
	
	/**
	 * 存储使用次数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/api/userapply")
	public String userapply(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> param = new HashMap<String, Object>();
	   String userid =request.getParameter("uid");
	   String phone = request.getParameter("phone");
	   String account =request.getParameter("account");
	   logger.info("########进入####/api/addUsers#######phone="+AesUtils.aesDecrypt(phone)+"account="+account+"##userid="+userid);
	   if(!CommonTools.validParam(phone,account,userid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
		   UserApply userApply  = new UserApply();
		   userApply.setPhone(AesUtils.aesDecrypt(phone));
		   userApply.setAccountNum(Integer.parseInt(account));
		   userApply.setUid(Integer.parseInt(userid));
	       int id =iUser.addUserApplyTable(userApply);//存储用户使用情况
	       if(id>0){
	    	param.put("uid", Integer.parseInt(userid));
	    	param.put("phone", AesUtils.aesDecrypt(phone));
	    	param.put("account", Integer.parseInt(account));
		    UserInfo userinfo =iUser.getUserInfoByAccount(param);
		    if(null !=userinfo){
		    	if(Integer.parseInt(account)==userinfo.getAccountNum()){
		    		param.put("type", 0);//次数用尽 金额及次数更改为0
		    		iUser.updateUserInfoTable(param);//更新useinfo
		    	}else{
		    		param.put("type", 1);//次数有剩余
		    		iUser.updateUserInfoTable(param);//更新useinfo
		    	}
		    }
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
	 * @throws Exception 
	 */
	@PostMapping(value="/api/investmoney")
	public String investmoney(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   Map<String, Object> mapper = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   String uid =request.getParameter("uid");
	   String phone = request.getParameter("phone");
	   String money =request.getParameter("money");
	   String account =request.getParameter("account");
	   logger.info("########进入####/api/investmoney#######phone="+AesUtils.aesDecrypt(phone)+"###money="+money+"account="+account+"##uid="+uid);
	   if(!CommonTools.validParam(phone,money,account,uid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
		   UserInfo userInfo  = new UserInfo();
		   userInfo.setName(AesUtils.aesDecrypt(phone));
		   userInfo.setPhone(AesUtils.aesDecrypt(phone));
		   userInfo.setUid(Integer.parseInt(uid));
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
	 * @throws Exception 
	 */
	@PostMapping(value="/api/getUserInfoByAccount")
	public String getUserInfoByAccount(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
	   Map<String, Object> param = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> result = new HashMap<String, Object>();
	   String phone = request.getParameter("phone");
	   String uid =request.getParameter("uid");
	   logger.info("########进入####/api/getUserInfoByAccount#######phone="+AesUtils.aesDecrypt(phone)+"##uid="+uid);
	   if(!CommonTools.validParam(phone,uid)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
	     param.put("phone", AesUtils.aesDecrypt(phone));
	     param.put("uid",Integer.parseInt(uid));
	     UserInfo userinfo =iUser.getUserInfoByAccount(param);
	     if(null !=userinfo){
	    	 map.put("personphone", userinfo.getPhone());
	    	 map.put("money", userinfo.getMoney()+"");
	    	 map.put("account", userinfo.getAccountNum()+"");
	    	 map.put("time", format.format(userinfo.getCreatetime())+"");
	     }else{
	    	 return FastJson.getFalse("0201", "无此用户信息！");
	     }
	     map.put("status","true");
	     result.put("result", map);
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
	 * @throws Exception 
	 */
	@PostMapping(value="/api/getUserApplyByList")
	public String getUserApplyByList(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   Map<String, Object> param = new HashMap<String, Object>();
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> result = new HashMap<String, Object>();
	   SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
	   String phone = request.getParameter("phone");
	   String uid =request.getParameter("uid");
	   String page =request.getParameter("page");
	   String pageSize =request.getParameter("pageSize");
	   logger.info("########进入####/api/getUserApplyByList#######phone="+AesUtils.aesDecrypt(phone)+"##uid="+uid
			   +"##page="+page+"##pageSize="+pageSize);
	   if(!CommonTools.validParam(phone,uid,page,pageSize)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
	     param.put("phone", AesUtils.aesDecrypt(phone));
	     param.put("uid",Integer.parseInt(uid));
	     int limit = Integer.parseInt(pageSize);
		 int offset = (Integer.parseInt(page)-1)*(Integer.parseInt(pageSize));
		 List<UserApply> listtotal =iUser.getUserApplyByList(param);
	     param.put("limit", limit);
	     param.put("offset", offset);
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
	     map.put("total", listtotal.size()+"");
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
	   SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
	   List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
	   String page =request.getParameter("page");
	   String pageSize =request.getParameter("pageSize");
	   String uid =request.getParameter("uid");
	   logger.info("########进入####/api/getAllUserInfoByList##uid="+uid+"##page="+page+"####pageSize="+pageSize);
	   if(!CommonTools.validParam(uid,page,pageSize)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   }
	   try {
	     param.put("uid",Integer.parseInt(uid));
	     int limit = Integer.parseInt(pageSize);
		 int offset = (Integer.parseInt(page)-1)*(Integer.parseInt(pageSize));
	     param.put("limit", limit);
	     param.put("offset", offset);
	     List<UserInfo> listtotal =iUser.getAllUserInfoByList(param);
	     param.put("type", 0);
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
	     map.put("total", listtotal.size()+"");
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
	 * 获取金额设置
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value="/api/getMoneySetingByList")
	public String getMoneySetingByList(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> result = new HashMap<String, Object>();
	   List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
	   logger.info("########进入####/api/getMoneySetingByList########");
	  
	   try {
	     List<MoneySet> list =iUser.getMoneySetingByList();
	     if(list.size()>0){
	    	 for(int i=0;i<list.size();i++){
	    		 Map<String, Object> retmap = new HashMap<String, Object>();
	    		 retmap.put("money", list.get(i).getMoney()+"");
	    		 retList.add(retmap);
	    	 }
	     }
	     map.put("dataList", retList);
	     map.put("status","true");
	     result.put("result", map);
        return FastJson.getResultFromMap(result);	 
	  } catch (Exception e) {
		e.printStackTrace();
		 return FastJson.getFalse("0200", "获取金额次数设置失败！");
      }
	}
	/**
	 * 获取次数设置进行联动
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value="/api/getAccountsSetting")
	public String getAccountsSetting(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> param = new HashMap<String, Object>();
	   Map<String, Object> result = new HashMap<String, Object>();
	   List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
	   logger.info("########进入####/api/getAccountsSetting########");
	   String moneys = request.getParameter("selmoneysel");
	   if(!CommonTools.validParam(moneys)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		}
	   try {
		 param.put("money", Integer.parseInt(moneys));
	     List<MoneySet> list =iUser.getAccountSettingByAccNum(param);
	     if(list.size()>0){
	    	 for(int i=0;i<list.size();i++){
	    		 Map<String, Object> retmap = new HashMap<String, Object>();
	    		 retmap.put("accounttimes", list.get(i).getAccountNum()+"");
	    		 retList.add(retmap);
	    	 }
	     }
	     map.put("dataList", retList);
	     map.put("status","true");
	     result.put("result", map);
        return FastJson.getResultFromMap(result);	 
	  } catch (Exception e) {
		e.printStackTrace();
		 return FastJson.getFalse("0200", "获取金额次数设置失败！");
      }
	}
	/**
	 * 获取金额设置
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value="/api/getMoneyList")
	public String getMoneyList(HttpServletRequest request,HttpServletResponse response){
	   Map<String, Object> map = new HashMap<String, Object>();
	   Map<String, Object> param = new HashMap<String, Object>();
	   Map<String, Object> result = new HashMap<String, Object>();
	   List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
	   logger.info("########进入####/api/getMoneyList########");
	   String money =request.getParameter("money");
	   if(!CommonTools.validParam(money)){
			 return FastJson.getFalse("0901", " 参数传递错误");
		   } 
	   try {
		 param.put("money", Integer.parseInt(money));
	     List<MoneySet> list =iUser.getMoneyByList(param);
	     if(list.size()>0){
	    	 for(int i=0;i<list.size();i++){
	    		 Map<String, Object> retmap = new HashMap<String, Object>();
	    		 retmap.put("money", list.get(i).getMoney()+"");
	    		 retList.add(retmap);
	    	 }
	     }
	     map.put("dataList", retList);
	     map.put("status","true");
	     result.put("result", map);
        return FastJson.getResultFromMap(result);	 
	  } catch (Exception e) {
		e.printStackTrace();
		 return FastJson.getFalse("0200", "获取金额次数设置失败！");
      }
	}
	
}
