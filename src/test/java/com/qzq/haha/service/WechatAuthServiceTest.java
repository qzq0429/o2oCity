package com.qzq.haha.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.dto.WechatAuthExecution;
import com.qzq.haha.entity.PersonInfo;
import com.qzq.haha.entity.WechatAuth;

public class WechatAuthServiceTest extends BaseTest {

	@Autowired
	private WechatAuthService wechatAuthService;
	
	@Test
	public void testRegister(){
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		String openId = "ADSFADSF";
		personInfo.setCreateTime(new Date());
		personInfo.setName("测试一下");
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		WechatAuthExecution wechatAuthExecution = wechatAuthService.register(wechatAuth);
	
		wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
	System.out.println(wechatAuth.getPersonInfo().getName());
	}
}
