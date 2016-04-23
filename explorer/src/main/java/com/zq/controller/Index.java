package com.zq.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zq.model.Customer;
import com.zq.util.HttpRequest;
import com.zq.util.MessageIntVo;
import com.zq.util.PageFrom;
@Controller
public class Index {

	@RequestMapping("/index")
	public ModelAndView showAboutUs(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();
		
		Customer customer=new Customer();
		customer.setCustomer_account_pwd("123124");
		customer.setCustomer_email("11@cc.com");
		customer.setCustomer_id("1");
		customer.setCustomer_name("zaa");
		customer.setCustomer_phone("18688701060");
		customer.setCustomer_pwd("123123");
		customer.setCustomer_state("0000");
		customer.setCustomer_type("1");
		//MessageIntVo<Customer> intVo=new MessageIntVo<Customer>();
		
		//intVo.setContent(customer);
	
		
		//mav.addObject("msg", JSON.toJSONString(intVo, true));
		mav.setViewName(PageFrom.getViewName(null, "index"));
	
		return mav;
	}
	
	@RequestMapping("/debugrequest")
	@ResponseBody
	public String debug(@RequestParam String appId, @RequestParam String token, @RequestParam String appkey, @RequestParam String actionName, @RequestParam String jsonString) {
		System.out.println("actionName="+actionName);
		System.out.println("jsonString="+jsonString);
		//JSONObject jsonObject = JSONObject.parseObject(jsonString);
		Map<String, Object> map = new HashMap<String, Object>();;
		map.put("ciphertext", jsonString);
		//***********************************************************
//		String serverUrl = "http://localhost:8080//fund-server/service";
		String serverUrl = "http://localhost:8080/explorer/"+actionName;
		//***********************************************************
		
	
		
		String result = HttpRequest.sendPost(serverUrl, map);
		//System.out.println("result="+result);
		return result;
	}
}
