package com.qzq.haha.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzq.haha.cache.JedisUtil;
import com.qzq.haha.cache.JedisUtil.Keys;
import com.qzq.haha.dao.AreaDao;
import com.qzq.haha.entity.Area;
import com.qzq.haha.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;

	private static String AREALISTKEY = "arealist";
	
	private static org.slf4j.Logger Logger = LoggerFactory.getLogger(AreaServiceImpl.class);
    @Override
	@Transactional
	public List<Area> getAreaList() {
		String key = AREALISTKEY;
		List<Area> areasList = null;
		ObjectMapper mapper = new ObjectMapper();
		if (!jedisKeys.exists(key)) {
			areasList = areaDao.queryArea();
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(areasList);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Logger.error(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areasList = mapper.readValue(jsonString, javaType);
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
		return areasList;
	}

}
