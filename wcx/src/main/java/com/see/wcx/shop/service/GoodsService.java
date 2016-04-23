package com.see.wcx.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.see.wcx.common.persistence.HibernateDao;
import com.see.wcx.common.service.BaseService;
import com.see.wcx.shop.dao.GoodsDao;
import com.see.wcx.shop.entity.Goods;

/**
 * 商品service
 * @author ty
 * @date 2015年1月22日
 */
@Service
@Transactional(readOnly=true)
public class GoodsService extends BaseService<Goods, Integer> {
	
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public HibernateDao<Goods, Integer> getEntityDao() {
		return goodsDao;
	}

}
