package com.zq.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zq.dao.CustomerMapper;
import com.zq.model.Customer;
import com.zq.util.MessageIntVo;
import com.zq.util.Page;
import com.zq.util.PageFrom;

@Service
public class CustomerSerice {
	
	private Logger logger = Logger.getLogger(CustomerSerice.class);
	
	@Resource(name = "customerMapper")
	private CustomerMapper customerMapper;
	
	
	/**
	 * 保存用户
	 * @param customer
	 * @throws Exception
	 */
	public int  saveCustomer(Customer customer) throws Exception {
		logger.info("保存用户  参数：customer="+JSON.toJSONString(customer));
		int i=customerMapper.insertCustomer(customer);
		logger.info("  保存用户结束返回数据：i="+i);
		return i;
	}
	
	/**
	 * 更新用户
	 * @param customer
	 * @throws Exception
	 */
	public void  modeCustomer(Customer customer) throws Exception {
		logger.info("更新用户  参数：customer="+JSON.toJSONString(customer));
		Map<String, Object> map=PageFrom.objToMap(customer);
		customerMapper.updateCustomer(map);;
		logger.info(" 更新用户结束返回！");
	}

	
	/**
	 * 分页查询
	 */
	public Page getCustomerPage(Page page,Customer customer) throws Exception {
		logger.info("  分页查询 getCustomerPage 参数：MessageIn:"+JSON.toJSONString(customer));
		Map<String, Object> map=PageFrom.objToMap(customer);
	
		map.put("startRow", page.getStartRow());
		map.put("endRow", page.getEndRow());
		
		List<Customer> list= 	customerMapper.selectCustomerList(map);
		page.setList(list);
		Integer i=0;
		if(list.size()>0){
			i=customerMapper.selectCountCustomerList(map);
		}
		page.setTotalCount(i.intValue());
		logger.info("  分页查询 getCustomerPage 返回list:"+JSON.toJSONString(page));
		return page;
	}
	
	
	
	
}
