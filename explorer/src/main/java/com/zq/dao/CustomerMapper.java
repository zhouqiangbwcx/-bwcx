package com.zq.dao;

import java.util.List;
import java.util.Map;

import com.zq.model.Customer;

public interface CustomerMapper {

	/**
	 * 保存数据
	 * 
	 * @param Customer
	 * @return
	 */
	public int insertCustomer(Customer customer);
	

	/**
	 * 更新数据
	 * 
	 * @param Customer
	 * @return
	 */
	public void updateCustomer(Map<String, Object> map);
	
	/**
	 * 查询用户list
	 * 
	 * @param Customer
	 * @return
	 */
	public List<Customer> selectCustomerList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 * 
	 * @param Customer
	 * @return
	 */
	public Integer selectCountCustomerList(Map<String, Object> map);
	

}
