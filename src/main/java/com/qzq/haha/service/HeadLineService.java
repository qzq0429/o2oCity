package com.qzq.haha.service;

import java.io.IOException;
import java.util.List;

import com.qzq.haha.entity.HeadLine;

public interface HeadLineService {

	
	
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
