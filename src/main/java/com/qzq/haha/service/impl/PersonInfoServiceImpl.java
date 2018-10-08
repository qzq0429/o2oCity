package com.qzq.haha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qzq.haha.dao.PersonInfoDao;
import com.qzq.haha.entity.PersonInfo;
import com.qzq.haha.service.PersonInfoService;
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		// TODO Auto-generated method stub
		return personInfoDao.queryPersonInfoById(userId);
	}

}
