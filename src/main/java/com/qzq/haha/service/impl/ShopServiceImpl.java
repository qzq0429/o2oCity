package com.qzq.haha.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.qzq.haha.dao.ShopDao;
import com.qzq.haha.dto.ImageHolder;
import com.qzq.haha.dto.ShopExecution;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.enums.ShopStateEnum;
import com.qzq.haha.exceptions.ShopOperationException;
import com.qzq.haha.service.ShopService;
import com.qzq.haha.util.ImageUtil;
import com.qzq.haha.util.PageCalculator;
import com.qzq.haha.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
	private ShopDao shopDao;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
		// TODO Auto-generated method stub
		if(shop == null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try{
			//给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
		    if(effectedNum<=0){
		    	throw new RuntimeException();
		    }else{
		    	if(shopImg!=null){
		    		//存储图片
		    		try{
		    		addShopImg(shop, shopImg);
		    		}catch(Exception e){
		    			throw new ShopOperationException("addShopImg error:"+e.getMessage());
		    		}
		    		effectedNum = shopDao.updateShop(shop);
		    		if(effectedNum<=0){
		    			throw new ShopOperationException("更新图片失败");
		    		}
		    	}
		    }
		}catch(Exception e){
			throw new ShopOperationException("addShop error:"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK);
	}
	private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
		// TODO Auto-generated method stub
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
	    shop.setShopImg(shopImgAddr);
	}
	@Override
	public Shop getByShopId(long shopId) {
		// TODO Auto-generated method stub
		return shopDao.queryByShopId(shopId);
	}
	@Override
	public ShopExecution modifyShop(Shop shop,ImageHolder thubmnail) {
		// TODO Auto-generated method stub
		if(shop==null||shop.getShopId()==null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else{String ImagePath=null;
			try{
			//1.判断是否需要处理图片
			if(thubmnail!=null){
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
			    if(tempShop.getShopImg()!=null){
			    	ImageUtil.deleteFileOrPath(tempShop.getShopImg());
			    }
			    addShopImg(tempShop, thubmnail.getImage());
			    ImagePath=tempShop.getShopImg();
			}
			//2.更新店铺信息
			if(ImagePath!=null)
			shop.setShopImg(ImagePath);
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.updateShop(shop);
			if(effectedNum<=0){
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}else{
				shop=shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}	
			}catch (Exception e) {
			// TODO: handle exception
				throw new ShopOperationException(e.getMessage());
		}
	}
		}
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution seExecution =new ShopExecution();
		if(shopList!=null){
			seExecution.setCount(count);
			seExecution.setShopList(shopList);
		}else{
			seExecution.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		
		return seExecution;
	}
	

}
