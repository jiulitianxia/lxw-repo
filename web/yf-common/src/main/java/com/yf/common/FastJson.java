package com.yf.common;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;

public class FastJson {

	public static String getOk() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "true");
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("result", map);
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(resultmap);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getFalse(int code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "false");
		map.put("code", String.valueOf(code));
		map.put("message", "null");
		
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("result", map);
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(resultmap);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getFalse(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "false");
		map.put("code", code);
		map.put("message", "null");
		
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("result", map);
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(resultmap);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getFalse(String code, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "false");
		map.put("code", code);
		map.put("message", message);
		
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("result", map);
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(resultmap);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getFalseForWeb(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "false");
		map.put("code", code);
		map.put("message", "null");
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(map);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getFalseForWeb(String code,String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "false");
		map.put("code", code);
		map.put("message", message);
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(map);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getTrueForWeb() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "true");
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(map);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getTrueForWeb(int i) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "true" + i);
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(map);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getResultFromMap(Map<String, Object> result) {
		/*ObjectMapper objectMapper = new ObjectMapper();
		 userJsonStr = objectMapper.writeValueAsString(result);*/
		String userJsonStr="";
		try {
			userJsonStr = JSON.toJSONString(result);
			return userJsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}