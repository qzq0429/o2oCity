package com.qzq.haha.dao;

import com.qzq.haha.entity.PersonInfo;

public interface PersonInfoDao {

	/**
	 * 通过用户Id查询用户
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);
	
	/**
	 * 添加用户信息
	 */
	int insertPersonInfo(PersonInfo personInfo);
}
