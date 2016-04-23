package com.see.wcx.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.see.wcx.common.persistence.HibernateDao;
import com.see.wcx.common.service.BaseService;
import com.see.wcx.shop.dao.GoodsTypeDao;
import com.see.wcx.shop.entity.GoodsType;

/**
 * 商品类型service
 * @author ty
 * @date 2015年1月22日
 */
@Service
@Transactional(readOnly=true)
public class GoodsTypeService extends BaseService<GoodsType, Integer> {
	
	@Autowired
	private GoodsTypeDao goodsTypeDao;

	@Override
	public HibernateDao<GoodsType, Integer> getEntityDao() {
		return goodsTypeDao;
	}

}
