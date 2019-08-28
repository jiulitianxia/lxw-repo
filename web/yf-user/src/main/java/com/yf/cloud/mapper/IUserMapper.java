package com.yf.cloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yf.info.UserTable;

@Mapper
public interface IUserMapper {

	public void saveUserInfo(UserTable usertable);

	public UserTable getUserTableLoginInfo(Map<String, Object> param);

	public UserTable getUserById(Map<String, Object> param);

	public List<UserTable> getAllUserInfo();

	public String getPassword(String username);

}
