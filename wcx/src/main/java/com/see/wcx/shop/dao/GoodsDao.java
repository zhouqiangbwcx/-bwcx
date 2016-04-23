package com.see.wcx.shop.dao;

import org.springframework.stereotype.Repository;

import com.see.wcx.common.persistence.HibernateDao;
import com.see.wcx.shop.entity.Goods;

/**
 * 商品DAO
 * @author ty
 * @date 2015年1月22日
 */
@Repository
public class GoodsDao extends HibernateDao<Goods, Integer>{

}
