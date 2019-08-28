package com.yf.common;
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
}
