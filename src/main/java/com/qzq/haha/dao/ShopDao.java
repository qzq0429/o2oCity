package com.qzq.haha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qzq.haha.entity.Shop;

public interface ShopDao {
	
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	/**
	 * 分页查询店铺，可输入条件有：店铺名（模糊），店铺状态，店铺类别，区域ID，owner
	 * @shopConditoin
	 * @rowIndex 从第几行开始取数据
	 * @param pageSize返回的条数
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
			@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
	/**
	 * 
	 * 新增店铺
	 * @param shop
	 * @return
	 */
    int insertShop(Shop shop);
    
    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int  updateShop(Shop shop);
    /**
     * 查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
}
