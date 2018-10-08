package com.qzq.haha.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.Product;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.entity.ProductImg;
import com.qzq.haha.entity.Shop;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	public void testABatchInsertProductIMG()throws Exception{
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
	}
	@Test
	public void testBInsertProduct() throws Exception {
		Shop shop1 = new Shop();
		shop1.setShopId(2L);
		Shop shop2 = new Shop();
		shop2.setShopId(8L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(2L);
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryId(2L);
		ProductCategory pc3 = new ProductCategory();
		pc3.setProductCategoryId(2L);
		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试Desc1");
		product1.setImgAddr("test1");
		product1.setPriority(0);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试Desc2");
		product2.setImgAddr("test2");
		product2.setPriority(0);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc2);
		Product product3 = new Product();
		product3.setProductName("测试3");
		product3.setProductDesc("测试Desc3");
		product3.setImgAddr("test3");
		product3.setPriority(0);
		product3.setEnableStatus(1);
		product3.setCreateTime(new Date());
		product3.setLastEditTime(new Date());
		product3.setShop(shop2);
		product3.setProductCategory(pc3);
		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product2);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product3);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testCqueryProductImgList(){
		List<ProductImg> List = productImgDao.queryProductImgList(1);
		for(ProductImg aImg : List){
			System.out.print(aImg.getImgDesc()+"00000");
		}
	}
	@Test
	public void testDDeleteProduct(){
		productImgDao.deleteProductImgByProductId(17l);
	}
	@Test
	public void testEUpdateProduct()throws Exception{
	Product product = productDao.queryProductByProductId(1l);
	ProductCategory pCategory = new ProductCategory();
/*	Shop shop = new Shop();
	shop.setShopId(2l);
	pCategory.setProductCategoryId(1l);
	product.setProductId(1l);
	product.setShop(shop);*/
	product.setProductName("第11一个产品");
	product.setProductCategory(pCategory);
	int effectedNum = productDao.updateProduct(product);
	}
}
