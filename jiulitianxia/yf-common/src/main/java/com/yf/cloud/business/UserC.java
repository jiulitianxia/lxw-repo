package com.yf.cloud.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yf.cloud.ibusiness.IUser;
import com.yf.cloud.info.UserTable;
import com.yf.cloud.mapper.IUserMapper;

@Service
public class UserC implements IUser {
	
	@Autowired
    private IUserMapper iUserMapper;
	@Override
	public int  saveUserInfo(UserTable usertable) {
		try {
			this.iUserMapper.saveUserInfo(usertable);
			return usertable.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public UserTable getUserTableLoginInfo(Map<String, Object> param) {
		 try {
			return this.iUserMapper.getUserTableLoginInfo(param);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public UserTable getUserById(Map<String, Object> param) {
		 try {
			return this.iUserMapper.getUserById(param);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<UserTable> getAllUserInfo() {
		 try {
			return this.iUserMapper.getAllUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
			return  new ArrayList<UserTable>();
		}
	}
}
