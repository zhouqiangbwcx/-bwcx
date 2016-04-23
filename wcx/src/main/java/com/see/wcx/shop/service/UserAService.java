package com.see.wcx.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.see.wcx.shop.entity.User;
import com.see.wcx.shop.mdao.UserMapper;

@Service
public class UserAService {
	@Autowired
	private UserMapper userMapper;
	public User getUserId(int id){
		User usr=userMapper.selectByPrimaryKey(id);
		return usr;
	}
}
