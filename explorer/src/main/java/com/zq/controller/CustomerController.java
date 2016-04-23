package com.zq.controller;

import java.util.List;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.zq.model.Customer;
import com.zq.service.CustomerSerice;
import com.zq.util.AssemblerErrorCode;
import com.zq.util.MessageIntVo;
import com.zq.util.MessageOutVo;
import com.zq.util.Page;
import com.zq.util.PageFrom;

@Controller
@RequestMapping("/Customer")
public class CustomerController {

	private Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	private CustomerSerice customerSerice;

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/customer")
	public void customer(HttpServletRequest request,
			HttpServletResponse response,	@RequestBody MessageIntVo intVo) {
		logger.info("用户登录开始");

		MessageOutVo out = new MessageOutVo();
		Customer customer=intVo.getMsg(Customer.class);
		if (PageFrom.isNoNullOrEmpty(customer)
				&& PageFrom.isNoNullOrEmpty(customer.getCustomer_pwd())) {
			try {
				Page page = new Page();
				page = customerSerice.getCustomerPage(page, customer);
				List customerList = (List) page.getList();
				if (customerList.size() == 1) {
					out.setMsg(customerList.get(0));
				} else {
					out.setErrerCode(AssemblerErrorCode.LOGICERROR);
					out.setMsg("用户名,手机号,邮箱或密码错误！");
				}
			} catch (Exception e) {
				out.setErrerCode(AssemblerErrorCode.SYSTEM_ERROR);
				out.setMsg(e.getMessage());
				logger.error("用户登录错误：", e);
			}
		} else {
			out.setErrerCode(AssemblerErrorCode.LOGICERROR);
			out.setMsg("密码不能为空！");
		}
		PageFrom.jsonResponseWriter(response, out);
		logger.info("更新一个用户结束");

	}

	/**
	 * 保存一个用户
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @param page
	 */

	@RequestMapping("/sava_customer")
	public void sava_customer(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody MessageIntVo intVo) {
		logger.info("保存一个用户开始 ");

		MessageOutVo out = new MessageOutVo();
		try {
			if(PageFrom.isNoNullOrEmpty(intVo.getMsg(Customer.class))){

				Customer customer =intVo.getMsg(Customer.class);
			    customerSerice.saveCustomer(customer);
				out.setMsg(customer);
			}else{
				out.setErrerCode("1000");
				out.setMsg("参数错误！");
			}
		} catch (Exception e) {
			out.setErrerCode(AssemblerErrorCode.SYSTEM_ERROR);
			out.setMsg(e.getMessage());
			logger.error("保存一个用户错误：", e);
		} finally {
			PageFrom.jsonResponseWriter(response, out);
			logger.info("保存一个用户结束");
		}
	}

	/**
	 * 更新一个用户
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @param page
	 */
	@RequestMapping("/mod_customer")
	public void mod_customer(HttpServletRequest request,
			HttpServletResponse response, @RequestBody MessageIntVo intVo) {
		logger.info(" 更新一个用户开始");

		MessageOutVo out = new MessageOutVo();
		try {
			Customer customer = (Customer) intVo.getMsg(Customer.class);
			int id = customerSerice.saveCustomer(customer);
			customer.setCustomer_id(id + "");
		} catch (Exception e) {
			out.setErrerCode(AssemblerErrorCode.SYSTEM_ERROR);
			out.setMsg(e.getMessage());
			logger.error(" 更新一个用户错误：", e);
		} finally {
			PageFrom.jsonResponseWriter(response, out);
			logger.info("更新一个用户结束");
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @param page
	 */
	@RequestMapping("/page_customer")
	public void page_customer(HttpServletRequest request,
			HttpServletResponse response, @RequestBody MessageIntVo intVo ) {
		logger.info(" 分页查询开始");
		 Page page = new Page();

		MessageOutVo out = new MessageOutVo();
		try {
			int CurrentPage=intVo.getMsg(JSONObject.class).getIntValue("currentPage");
			System.out.println(CurrentPage);
		   int ShowRecords=	intVo.getContent().getJSONObject("msg").getIntValue("showRecords");
			System.out.println(ShowRecords);
		   
			page.setCurrentPage(CurrentPage);
			page.setShowRecords(ShowRecords);
			
			Customer customer=intVo.getMsg(Customer.class);
			page = customerSerice.getCustomerPage(page, customer);
			out.setMsg(page);
		} catch (Exception e) {
			out.setErrerCode(AssemblerErrorCode.SYSTEM_ERROR);
			out.setMsg(e.getMessage());
			logger.error("分页查询错误：", e);
		} finally {
			PageFrom.jsonResponseWriter(response, out);
			logger.info("分页查询结束");
		}

	}
}
