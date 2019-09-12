package com.yf.cloud.ibusiness;

import java.util.List;
import java.util.Map;

import com.yf.cloud.info.UserApply;
import com.yf.cloud.info.UserInfo;
import com.yf.cloud.info.UserTable;

public interface IUser {

	public int saveUserInfo(UserTable usertable);

	public UserTable getUserTableLoginInfo(Map<String, Object> param);

	public int getUserById(Map<String, Object> param);

	public List<UserTable> getAllUserInfo();

	public int addUserInfo(UserInfo userInfo);

	public int addUserApplyTable(UserApply userApply);

	public void updateUserInfoTable(Map<String, Object> param);

	public void updateInvestMoneyUserInfo(UserInfo userInfo);

	public UserInfo getUserInfoByAccount(Map<String, Object> param);

	public List<UserApply> getUserApplyByList(Map<String, Object> param);

	public List<UserInfo> getAllUserInfoByList(Map<String, Object> param);

}
