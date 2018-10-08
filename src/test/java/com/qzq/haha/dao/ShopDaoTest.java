package com.qzq.haha.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.Area;
import com.qzq.haha.entity.PersonInfo;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.entity.ShopCategory;

public class ShopDaoTest extends BaseTest {
        @Autowired
        ShopDao shopDao;
        
        @Test
        @Ignore
        public void insertShopTest(){
        	Shop shop = new Shop();
        	Area area = new Area();
        	PersonInfo personInfo = new PersonInfo();
        	ShopCategory shopCategory = new ShopCategory();
        	area.setAreaId(1);
        	personInfo.setUserId(1l);
        	shopCategory.setShopCategoryId(1l);
        	shop.setAdvice("待审核");
        	shop.setArea(area);
        	shop.setCreateTime(new Date());
        	shop.setEnableStatus(1);
        	shop.setLastEditTime(new Date());
        	shop.setOwner(personInfo);
        	shop.setPhone("1354664687");
        	shop.setPriority(4);
        	shop.setShopAddr("是电脑发几款");
        	shop.setShopCategory(shopCategory);
        	shop.setShopDesc("测试中");
        	shop.setShopImg("kadsjflkjifaioe");
        	shop.setShopName("测试中的商铺");
        	int EffectNum=shopDao.insertShop(shop);
        	assertEquals(1,EffectNum);
        }
       @Test
       @Ignore
        public void updateShopTest(){
        	Shop shop = new Shop();
        	
        	shop.setShopId(2l);
        
        	shop.setShopAddr("测试地址2");
        
        	shop.setShopDesc("测试描述");
        	
        	int EffectNum=shopDao.updateShop(shop);
        	assertEquals(1,EffectNum);
        }
        @Test
        @Ignore
        public void queryByshopIdTest(){
        	Shop shop = shopDao.queryByShopId(2l);
        	long num = shop.getShopId();
        	assertEquals(2l,num);
        }
        @Test
        public void tesQueryShopList(){
/*        	Shop shopCondition = new Shop();
        	PersonInfo owner = new PersonInfo();
        	owner.setUserId(1l);
        	shopCondition.setOwner(owner);*/
        	
        	Shop shopCondition = new Shop();
            ShopCategory child = new ShopCategory();
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(10l);
            child.setParent(parent);
            shopCondition.setShopCategory(child);
            PersonInfo owner = new PersonInfo();
        	owner.setUserId(1l);
        	shopCondition.setOwner(owner);
        	
        	int count  = shopDao.queryShopCount(shopCondition);
        	List<Shop> shoplist = shopDao.queryShopList(shopCondition, 0, 5);
            System.out.println("店铺列表的大小："+shoplist.size());
            System.out.println("店铺总数："+count);
        }
}

