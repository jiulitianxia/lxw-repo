package com.yf.cloud.info;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 用户信息表
 * @author lxw
 *
 */
@Data
public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int    id;
	private String name;
	private String phone;//用户手机号
    private int    money;//充值金额
	private int    accountNum;//可使用次数
	private Date   createtime;
	private String db_source;
}
