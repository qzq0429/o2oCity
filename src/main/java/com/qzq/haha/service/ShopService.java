package com.qzq.haha.service;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.qzq.haha.dto.ImageHolder;
import com.qzq.haha.dto.ShopExecution;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition分页返回相应店铺列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	/**
	 * 注册店铺信息，包括图片处理
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	public ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg) throws ShopOperationException;
    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
	public Shop getByShopId(long shopId);
    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     */
    public ShopExecution modifyShop(Shop shop,ImageHolder thubmnail)throws ShopOperationException;
}
