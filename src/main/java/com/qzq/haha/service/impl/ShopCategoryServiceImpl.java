package com.qzq.haha.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.qzq.haha.dao.ProductCategoryDao;
import com.qzq.haha.dao.ShopCategoryDao;
import com.qzq.haha.dto.ProductCategoryExecution;
import com.qzq.haha.dto.ShopExecution;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.entity.ShopCategory;
import com.qzq.haha.exceptions.ProductCategoryOperationException;
import com.qzq.haha.service.ShopCategoryService;
import com.qzq.haha.service.ShopService;
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
@Autowired
private ShopCategoryDao shopCategory;
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		// TODO Auto-generated method stub
		return shopCategory.queryShopCategory(shopCategoryCondition);
	}



}
