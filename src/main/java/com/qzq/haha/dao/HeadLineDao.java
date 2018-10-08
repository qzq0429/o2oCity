package com.qzq.haha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qzq.haha.entity.HeadLine;

public interface HeadLineDao {

	/**
	 * 根据传入的查询条件（头条名查询头条）
	 * @param headLine
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCondition")HeadLine headLine);
	
}
