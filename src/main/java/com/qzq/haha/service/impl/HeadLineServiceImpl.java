package com.qzq.haha.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qzq.haha.dao.HeadLineDao;
import com.qzq.haha.entity.HeadLine;
import com.qzq.haha.service.HeadLineService;
@Service
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
	private HeadLineDao headlineDao;
	
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		// TODO Auto-generated method stub
		return headlineDao.queryHeadLine(headLineCondition);
	}

}
