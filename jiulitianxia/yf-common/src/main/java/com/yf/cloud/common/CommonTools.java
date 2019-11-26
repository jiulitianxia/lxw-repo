package com.yf.cloud.common;
/**
 * 
 * @author lxw
 *
 */
public class CommonTools {
	public static boolean validParam(String... args) {
		for (int i = 0; i < args.length; i++) {
			if (null == args[i] || "".equals(args[i].trim())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 格式化电话号码
	 * @param phone
	 * @return
	 */
	public static String formatePhone(String phone){
		String formatphone =phone.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1 $2 $3");
		return formatphone;
	}
}
