package com.qzq.haha.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.ProductCategory;

public class ProductCategoryServiceTest extends BaseTest {
@Autowired
private ProductCategoryService productCategoryService;

@Test
public void getProductCategoryServiceTest(){
	List<ProductCategory> productCategory = productCategoryService.getProductCategoryList(12);
	System.out.println(productCategory.size());
}
}
