package com.qzq.haha.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.ProductCategory;

public class ProductCategoryTest extends BaseTest{

	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Test
	@Ignore
	public void testQueryByShopId()throws Exception{
		long shopId=12;
		List<ProductCategory> productCategorylist = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println("该店铺自定义类别数为："+productCategorylist.size());
	}
	
    @Test
    @Ignore
    public void testBatchinsert(){
    	ProductCategory productCategory = new ProductCategory();
    	productCategory.setProductCategoryName("商品类别1");
    	productCategory.setPriority(5);
    	productCategory.setCreateTime(new Date());
    	productCategory.setShopId(2l);
    	ProductCategory productCategory1 = new ProductCategory();
    	productCategory1.setProductCategoryName("商品类别2");
    	productCategory1.setPriority(6);
    	productCategory1.setCreateTime(new Date());
    	productCategory1.setShopId(2l);
    	List<ProductCategory> list = new ArrayList<ProductCategory>();
    	list.add(productCategory);
    	list.add(productCategory1);
    	int effectedNum = productCategoryDao.batchInsertProductCategory(list);
    }
    @Test
    public void testDelete(){
    
		long shopId=2;
		List<ProductCategory> productCategorylist = productCategoryDao.queryProductCategoryList(shopId);
		for(ProductCategory productc:productCategorylist)
		productCategoryDao.deleteProductCategory(productc.getProductCategoryId(), 2);
    }
}
