package com.qzq.haha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.exceptions.ProductCategoryOperationException;

public interface ProductCategoryDao {

	/**
	 * 返回商品类别
	 * @param shopid
	 * @return
	 */
	public List<ProductCategory> queryProductCategoryList(long shopid);
	/**
	 * 批量增加商品类别
	 * @param productCategoryList
	 * @return
	 */
	public Integer batchInsertProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;
/**
 * 
 */
    public Integer deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
}
