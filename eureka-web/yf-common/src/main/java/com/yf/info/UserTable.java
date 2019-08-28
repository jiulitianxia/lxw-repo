package com.yf.info;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  static final String Table = "t_user";
	private int id;
	private String name;
	private String username;
    private String password;
	private String phone;
	private int age;
	private int sex;
	private Date  createtime;
	private String db_source;
	
}
