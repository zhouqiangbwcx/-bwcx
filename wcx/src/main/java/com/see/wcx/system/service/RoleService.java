package com.see.wcx.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.see.wcx.common.persistence.HibernateDao;
import com.see.wcx.common.service.BaseService;
import com.see.wcx.system.dao.RoleDao;
import com.see.wcx.system.entity.Role;

/**
 * 角色service
 * @author ty
 * @date 2015年1月13日
 */
@Service
@Transactional(readOnly = true)
public class RoleService extends BaseService<Role, Integer> {

	@Autowired
	private RoleDao roleDao;

	@Override
	public HibernateDao<Role, Integer> getEntityDao() {
		return roleDao;
	}
}
