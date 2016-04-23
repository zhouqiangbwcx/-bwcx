package com.see.wcx.system.dao;

import org.springframework.stereotype.Repository;

import com.see.wcx.common.persistence.HibernateDao;
import com.see.wcx.system.entity.Dict;

/**
 * 字典DAO
 * @author ty
 * @date 2015年1月13日
 */
@Repository
public class DictDao extends HibernateDao<Dict, Integer>{

}
