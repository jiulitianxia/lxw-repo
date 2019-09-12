package com.yf.cloud.info;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户使用情况
 * @author lxw
 *
 */
@Data
public class UserApply implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int    id;
	private String phone;//用户手机号
	private int    accountNum;//使用次数
	private Date   createtime;
	private Date   updatetime;
	private int    uid;
	private String db_source;
}
