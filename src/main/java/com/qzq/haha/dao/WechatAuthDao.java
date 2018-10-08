package com.qzq.haha.dao;

import com.qzq.haha.entity.WechatAuth;

public interface WechatAuthDao {

	
	WechatAuth queryWechatInfoByOpenId(String openId);

	int insertWechatAuth(WechatAuth wechatAuth);
}
