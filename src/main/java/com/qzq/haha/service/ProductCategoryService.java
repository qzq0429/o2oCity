package com.qzq.haha.service;

import java.util.List;

import com.qzq.haha.dto.ProductCategoryExecution;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {

	
	
	List<ProductCategory> getProductCategoryList(long shopId);
	
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList);

	public ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) 
	       throws ProductCategoryOperationException;
}
