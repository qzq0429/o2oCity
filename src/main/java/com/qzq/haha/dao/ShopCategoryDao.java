package com.qzq.haha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qzq.haha.entity.ShopCategory;

public interface ShopCategoryDao {
  List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
  
}
