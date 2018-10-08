package com.qzq.haha.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.HttpServletBean;

public class HttpServletRequestUtil {
	/**
	 * 将key对应的值转换为int类型;
	 * @param request
	 * @param key
	 * @return
	 */
public static int getInt(HttpServletRequest request,String key){
	try{
	return Integer.decode(request.getParameter(key));
	}catch (Exception e) {
		// TODO: handle exception
		return -1;
	}
}
/**
 * 将key对应的值转换为Long类型
 * @param request
 * @param key
 * @return
 */
public static Long getLong(HttpServletRequest request,String key){
	try {
		return Long.valueOf(request.getParameter(key));
	} catch (Exception e) {
		// TODO: handle exception
		return -1l;
	}
}
/**
 * 将key对应的值转换为Double类型
 * @param request
 * @param key
 * @return
 */
public static Double getDouble(HttpServletRequest request,String key){
	try {
		return Double.valueOf(request.getParameter(key));
	} catch (Exception e) {
		// TODO: handle exception
		return -1d;
	}
}
/**
 * 将key对应的值转换为Float类型
 * @param request
 * @param key
 * @return
 */
public static Float getFloat(HttpServletRequest request,String key){
	try {
		return Float.valueOf(request.getParameter(key));
	} catch (Exception e) {
		// TODO: handle exception
		return -1f;
	}
}
/**
 * 将key对应的值转换为Boolean类型
 * @param request
 * @param key
 * @return
 */
public static Boolean getBoolean(HttpServletRequest request,String key){
	try {
		return Boolean.valueOf(request.getParameter(key));
	} catch (Exception e) {
		// TODO: handle exception
		return false;
	}
	
}

public static String getString(HttpServletRequest request,String key){
	try {
		String result = request.getParameter(key);
		if(result!=null){
			result=result.trim();
		}
		if("".equals(result)){
			result=null;
		}
		return result;
 
	} catch (Exception e) {
		// TODO: handle exception
		return null;
	}
}
}
