package org.warmsheep.encoder.util;


/**
 * 字符串的工具类
 * 
 */
public class StringUtil {
	/**
	 * 将多个字符串(或者对象)并成一个字符串
	 * 
	 * @param args
	 * @return
	 */
	public static String merge(Object... args) {
		StringBuffer info = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			info.append(args[i]);
		}
		return info.toString();
	}

	/**
	 * 判断字符串对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if ("".equals(obj.toString().trim())) {
			return true;
		}
		return false;
	}
}
