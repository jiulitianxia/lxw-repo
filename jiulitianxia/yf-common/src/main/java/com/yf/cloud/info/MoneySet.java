package com.yf.cloud.info;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MoneySet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private int money;//金额
	private int accountNum;//次数
	private Date  createtime;
	private String db_source;

}
