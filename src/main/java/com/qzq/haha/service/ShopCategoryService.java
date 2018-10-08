package com.qzq.haha.service;

import java.util.List;

import com.qzq.haha.dto.ProductCategoryExecution;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.entity.ShopCategory;

public interface ShopCategoryService {
	
public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);


}
