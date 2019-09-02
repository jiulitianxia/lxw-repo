package com.yf.cloud.info;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 用户登陆信息
 * @author lxw
 *
 */
@Data
public class UserTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String username;
    private String password;
	private Date  createtime;
	private String db_source;
	
}
