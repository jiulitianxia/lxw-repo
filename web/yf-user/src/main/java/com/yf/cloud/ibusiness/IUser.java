package com.yf.cloud.ibusiness;

import java.util.List;
import java.util.Map;
import com.yf.info.UserTable;

public interface IUser {

	public int saveUserInfo(UserTable usertable);

	public UserTable getUserTableLoginInfo(Map<String, Object> param);

	public UserTable getUserById(Map<String, Object> param);

	public List<UserTable> getAllUserInfo();

	public String getPassword(String username);

}
