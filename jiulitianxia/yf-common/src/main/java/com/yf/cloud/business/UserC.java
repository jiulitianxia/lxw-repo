package com.yf.cloud.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yf.cloud.ibusiness.IUser;
import com.yf.cloud.info.UserApply;
import com.yf.cloud.info.UserInfo;
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
	public int getUserById(Map<String, Object> param) {
		 try {
			return this.iUserMapper.getUserById(param);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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
	@Override
	public int addUserInfo(UserInfo userInfo) {
		try {
			this.iUserMapper.addUserInfo(userInfo);
			return userInfo.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return  0;
		}
	}
	@Override
	public int addUserApplyTable(UserApply userApply) {
		try {
			 this.iUserMapper.addUserApplyTable(userApply);
			 return userApply.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return  0;
		}
	}
	@Override
	public void updateUserInfoTable(Map<String, Object> param) {
		try {
			this.iUserMapper.updateUserInfoTable(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateInvestMoneyUserInfo(UserInfo userInfo) {
		try {
			this.iUserMapper.updateInvestMoneyUserInfo(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public UserInfo getUserInfoByAccount(Map<String, Object> param) {
		try {
			return this.iUserMapper.getUserInfoByAccount(param);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<UserApply> getUserApplyByList(Map<String, Object> param) {
		try {
			return this.iUserMapper.getUserApplyByList(param);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<UserApply>();
		}
	}
	@Override
	public List<UserInfo> getAllUserInfoByList(Map<String, Object> param) {
		try {
			return this.iUserMapper.getAllUserInfoByList(param);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<UserInfo>();
		}
	}
}
