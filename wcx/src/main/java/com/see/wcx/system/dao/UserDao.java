package com.see.wcx.system.dao;

import org.springframework.stereotype.Repository;

import com.see.wcx.common.persistence.HibernateDao;
import com.see.wcx.system.entity.User;


/**
 * 用户DAO
 * @author ty
 * @date 2015年1月13日
 */
@Repository
public class UserDao extends HibernateDao<User, Integer>{

}
