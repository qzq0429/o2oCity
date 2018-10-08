package com.qzq.haha.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.HeadLine;

public class HeadLineDaoTest extends BaseTest {
   @Autowired
   private HeadLineDao HeadLineDao;
	
	@Test
	public void querytest(){
		List<HeadLine> headLines = HeadLineDao.queryHeadLine(new HeadLine());
		assertEquals(1,headLines.size());
	}
}
