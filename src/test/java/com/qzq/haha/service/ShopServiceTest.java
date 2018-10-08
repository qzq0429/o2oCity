package com.qzq.haha.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.qzq.haha.BaseTest;
import com.qzq.haha.dto.ShopExecution;
import com.qzq.haha.entity.Area;
import com.qzq.haha.entity.PersonInfo;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.entity.ShopCategory;
import com.qzq.haha.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;
    
   
    /*public void testAddShop(){
    	Shop shop = new Shop();
    	Area area = new Area();
    	PersonInfo personInfo = new PersonInfo();
    	ShopCategory shopCategory = new ShopCategory();
    	area.setAreaId(1);
    	personInfo.setUserId(1l);
    	shopCategory.setShopCategoryId(1l);
    	shop.setAdvice("审核中");
    	shop.setArea(area);
    	shop.setCreateTime(new Date());
    	shop.setEnableStatus(1);
    	shop.setLastEditTime(new Date());
    	shop.setOwner(personInfo);
    	shop.setPhone("test1");
    	shop.setPriority(4);
    	shop.setShopAddr("test1");
    	shop.setShopCategory(shopCategory);
    	shop.setShopDesc("测试中");
    	shop.setShopImg("kadsjflkjifaioe");
    	shop.setShopName("测试中的商铺");
    	CommonsMultipartFile shopImg = new CommonsMultipartFile("D:/Imges/back.jpg");
    	//ShopExecution EffectNum=shopService.addShop(shop,shopImg);
    	//assertEquals(ShopStateEnum.CHECK.getState(),EffectNum.getState());
    	
    }*/
    @Test 
    public void testGetShopList(){
    	Shop shopCondition = new Shop();
    	ShopCategory sCategory = new ShopCategory();
    	sCategory.setShopCategoryId(1l);
    	shopCondition.setShopCategory(sCategory);
        ShopExecution se = shopService.getShopList(shopCondition, 3, 5);    	
    System.out.println("店铺列表："+se.getShopList().size());
    System.out.println("店铺总数："+se.getCount());
    }
}
