package com.qzq.haha.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.PersonInfo;

public class PersonInfoDaoTest extends BaseTest {
@Autowired
private PersonInfoDao personInfoDao;

@Test
@Ignore
public void testAinsertPersonInfo() throws Exception{
	PersonInfo personInfo = new PersonInfo();
	personInfo.setName("我爱你");
	personInfo.setGender("女");
	personInfo.setUserType(1);
	personInfo.setCreateTime(new Date());
	personInfo.setLastEditTime(new Date());
	personInfo.setEnableStatus(1);
	int effectedNum = personInfoDao.insertPersonInfo(personInfo);
    assertEquals(1,effectedNum);
}
@Test
public void testBQueryPersonInfo(){
	PersonInfo sInfo = personInfoDao.queryPersonInfoById(1);
	String ss=sInfo.getName();
	System.out.println(ss);
}
}
