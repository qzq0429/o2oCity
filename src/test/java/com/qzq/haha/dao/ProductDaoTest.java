package com.qzq.haha.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.Product;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.entity.Shop;

public class ProductDaoTest extends BaseTest {

	@Autowired
	ProductDao productDao;
	
	@Test
	@Ignore
	public void insertDao(){
		Product product = new Product();
		product.setProductName("ad00");
		product.setProductDesc("dsaf");
		product.setImgAddr("adsfs");
		product.setNormalPrice("4563");
		product.setPromotionPrice("454");
		product.setPriority(12);
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		product.setEnableStatus(11);
		ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryId(2l);
		Shop shop = new Shop();
		shop.setShopId(2l);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		int effectednum = productDao.insertProduct(product);
	}
	@Test
	public void query(){
		Shop shop = new Shop();
		shop.setShopId(2l);
		Product productCondition = new Product();
		productCondition.setShop(shop);
		int c=productDao.queryProductCount(productCondition);
		List<Product> products = productDao.queryProductList(productCondition, 0, 99);
	int effect = productDao.updateProductCategoryToNull(1l);
	}
}
