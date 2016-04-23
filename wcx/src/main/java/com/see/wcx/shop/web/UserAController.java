package com.see.wcx.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.see.wcx.shop.entity.User;
import com.see.wcx.shop.service.GoodsService;
import com.see.wcx.shop.service.UserAService;

@Controller
@RequestMapping("shop/usera")
public class UserAController {
	@Autowired
	private UserAService userAService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "show/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User updateForm(@PathVariable("id") Integer id) {
		User user=userAService.getUserId(id);
		return user;
	}
}
