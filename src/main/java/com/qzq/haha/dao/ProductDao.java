package com.qzq.haha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qzq.haha.entity.Product;

public interface ProductDao {
	List<Product> queryProductList(@Param("productCondition") Product productCondition,
			@Param("rowIndex") int beginIndex, @Param("pageSize") int pageSize);
	
    int queryProductCount(@Param("productCondition") Product productCondition);
	
    Product queryProductByProductId(long productId);


	int insertProduct(Product product);
	
	int updateProduct(Product product);
  
	int updateProductCategoryToNull(long productCategoryId);
	
}
