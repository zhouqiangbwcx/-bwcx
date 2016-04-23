package com.zq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MessageIntVo {
	
	public String ciphertext;// 密文
	public JSONObject  content;//内容
	private Object msg;
	/**
	 * 密文
	 * @return
	 */
	public String getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
		this.content=JSON.parseObject(this.ciphertext);
	}

	public JSONObject getContent() {
		return content;
	}

	public <T> T  getMsg(Class<T> clazz) {
		String strObj = content.getString("msg");
		if(PageFrom.isNoNullOrEmpty(strObj)){
			msg=JSONObject.parseObject(strObj, clazz);
			return	(T) msg;
		}else{
			return null;
		}
	}


	



	


	



}
