package com.qzq.haha.dao;

import java.io.IOException;

import org.junit.Test;
import org.omg.CORBA.StringHolder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzq.haha.BaseTest;
import com.qzq.haha.dto.WechatUser;
import com.qzq.haha.util.wechat.WechatUtil;

public class OcleTest extends BaseTest {

	@Test
	public void haha(){
		String str = "{\"openid\":\"oBxlb0yNxKfixe7PdHybOR5ER1Kg\",\"nickname\":\"Qzq\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"阿曼\",\"headimgurl\":\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/C1sibqIn3C3bqHZCa35GLyADazJiaL4Yp2eQic2SXKuok69I61zmRQFBfcjbMHC1qZRa0J7M9LyjAXaFj1sE7zduw\\/132\",\"privilege\":[]}"
;
		String str2 = "{'openid':1}";

		ObjectMapper mapper = new ObjectMapper();
		try {
			WechatUser aUser =mapper.readValue(str2,WechatUser.class);
			System.out.println(aUser);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 
	}
}
