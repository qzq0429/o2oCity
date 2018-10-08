package com.qzq.haha.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.Area;

public class AreaDaoTest extends BaseTest {
   @Autowired
   private AreaDao areaDao;
   @Test
   public void testQueryArea(){
	   
	   List<Area> areaList = areaDao.queryArea();
       System.out.print("123");
   }
}
