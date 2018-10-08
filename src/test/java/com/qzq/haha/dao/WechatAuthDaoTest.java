package com.qzq.haha.dao;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.PersonInfo;
import com.qzq.haha.entity.WechatAuth;

public class WechatAuthDaoTest extends BaseTest{
	
	@Autowired
	WechatAuthDao wechatAuthDao;
	
	@Test
	@Ignore
	public void TestinsertWechat(){
		WechatAuth wAuth = new WechatAuth();
		wAuth.setOpenId("werwer");
		wAuth.setCreateTime(new Date());
		wAuth.setWechatAuthId(1l);
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1l);
		wAuth.setPersonInfo(personInfo);
		wechatAuthDao.insertWechatAuth(wAuth);
	}
	
	@Test
	public void TestQueryWechat(){
		WechatAuth wAuth= wechatAuthDao.queryWechatInfoByOpenId("werwer");
		wAuth.getOpenId();
		wAuth.getPersonInfo();
		wAuth.getCreateTime();
		
	}

}
