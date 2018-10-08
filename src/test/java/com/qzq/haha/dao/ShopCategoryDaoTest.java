package com.qzq.haha.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void testQueryShopCategory(){
    	List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);

    int k = shopCategoryList.size();
    }

}
