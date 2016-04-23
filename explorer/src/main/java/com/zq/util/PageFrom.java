package com.zq.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class PageFrom {
	
	/**
     * json string 转换为 map 对象
     * @param jsonObj
     * @return
     */
    public static Map<String, Object> objToMap(Object obj) {
  
        Map<String, Object> map = (Map<String, Object>) JSON.toJSON(obj);
        return map;
    }

    /**json string 转换为 对象
     * @param jsonObj
     * @param type
     * @return
     */
    public  static <T>  T jsonToBean(String str, Class<T> type) {
    	        
        return JSON.parseObject(str, type);  
    } 


	 
	/**
	 * 返回json
	 * 
	 * @param response
	 * @param obj
	 *            返回消息
	 */
	public static void jsonResponseWriter(HttpServletResponse response,
			Object obj) {
		try {
			response.setContentType("text/text;charset=UTF-8");  
			response.getWriter().print(JSON.toJSONString(obj, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 判断是否为空,实体类是否new
	 * 
	 * @param obj
	 * @return false为空或“”或没new,true不为空或“”或已new
	 * @Description: 不能判断实体类是否有值
	 */
	public static boolean isNoNullOrEmpty(Object obj) {
		boolean bl = false;
		if (null != obj) {
			if (!"".equals(obj.toString())) {
				bl = true;
			}
		}
		return bl;
	}
	
	/**
	 * 判断是否为空,实体类是否new
	 * 
	 * @param obj
	 * @return ture为空或“”或没new,false不为空或“”或已new
	 * @Description: 不能判断实体类是否有值
	 */
	public static boolean isNullOrEmpty(Object obj) {
		boolean bl = true;
		if (null != obj) {
			if (!"".equals(obj.toString())) {
				bl = false;
			}
		}
		return bl;
	}

	/**
	 * 组装返回视图地址
	 * 
	 * @param site
	 *            jsp目录地址
	 * @param strPage
	 *            jsp名称
	 * @return
	 * @Description: site可以为空
	 */
	public static String getViewName(String site, String strPage) {
		String strView = "";
		if (isNoNullOrEmpty(site)) {
			strView = site + "/";
		}
		strView = strView + strPage;
		return strView;
	}
}
