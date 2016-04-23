package com.see.wcx.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.see.wcx.common.persistence.HibernateDao;
import com.see.wcx.common.service.BaseService;
import com.see.wcx.system.dao.UserOrgDao;
import com.see.wcx.system.entity.UserOrg;

/**
 * 用户机构Service
 * @author kkomge
 * @date 2015年5月9日 
 */
@Service
@Transactional(readOnly = true)
public class UserOrgService extends BaseService<UserOrg, Integer> {

	@Autowired
	private UserOrgDao userOrgDao;

	@Override
	public HibernateDao<UserOrg, Integer> getEntityDao() {
		return userOrgDao;
	}

	/**
	 * 添加修改用户机构
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateUserOrg(Integer userId,List<Integer> newList) {
		// 删除老的机构关系
		userOrgDao.deleteUO(userId);
		// 添加新的机构关系
		for (Integer integer : newList) {
			userOrgDao.save(new UserOrg(userId, integer));
		}
	}

	/**
	 * 获取用户拥有机构id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getOrgIdList(Integer userId) {
		return userOrgDao.findOrgIds(userId);
	}

}
