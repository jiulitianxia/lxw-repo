package com.yf.cloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yf.cloud.info.MoneySet;
import com.yf.cloud.info.UserApply;
import com.yf.cloud.info.UserInfo;
import com.yf.cloud.info.UserTable;

@Mapper
public interface IUserMapper {

	public void saveUserInfo(UserTable usertable);

	public UserTable getUserTableLoginInfo(Map<String, Object> param);

	public int getUserById(Map<String, Object> param);

	public List<UserTable> getAllUserInfo();

	public int addUserInfo(UserInfo userInfo);

	public int addUserApplyTable(UserApply userApply);

	public void updateUserInfoTable(Map<String, Object> param);

	public void updateUserInfoTable(UserInfo userInfo);

	public void updateInvestMoneyUserInfo(UserInfo userInfo);

	public UserInfo getUserInfoByAccount(Map<String, Object> param);

	public List<UserApply> getUserApplyByList(Map<String, Object> param);

	public List<UserInfo> getAllUserInfoByList(Map<String, Object> param);

	public List<MoneySet> getMoneySetingByList();

	public List<MoneySet> getAccountSettingByAccNum(Map<String, Object> param);

	public List<MoneySet> getMoneyByList(Map<String, Object> param);

	public void updateUserInfo(UserInfo userInfo);

	public String getPasswordByUsername(String username);

}
