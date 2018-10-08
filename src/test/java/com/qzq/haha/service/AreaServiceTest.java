package com.qzq.haha.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzq.haha.BaseTest;
import com.qzq.haha.entity.Area;

public class AreaServiceTest extends BaseTest {
     @Autowired
     private AreaService areaService;
     @Test
     public void testGetAreaList(){
    	 List<Area> areaList = areaService.getAreaList();

     }
}
