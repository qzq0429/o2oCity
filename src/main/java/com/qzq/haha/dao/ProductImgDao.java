package com.qzq.haha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qzq.haha.entity.ProductImg;

public interface ProductImgDao {
	
List<ProductImg> queryProductImgList(long productId);

int batchInsertProductImg(List<ProductImg> productImgList);

int deleteProductImgByProductId(@Param("productId")long productId);




}
