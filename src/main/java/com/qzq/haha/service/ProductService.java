package com.qzq.haha.service;

import java.awt.Image;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.qzq.haha.dto.ImageHolder;
import com.qzq.haha.dto.ProductCategoryExecution;
import com.qzq.haha.dto.ProductExecution;
import com.qzq.haha.dto.ShopExecution;
import com.qzq.haha.entity.Product;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.exceptions.ProductOperationException;
import com.qzq.haha.exceptions.ShopOperationException;

public interface ProductService {
/**
 * 
 * @param product
 * @param thumbnail
 * @param thumbnailName
 * @param productImgList
 * @param productImgNameList
 * @return ProductExecution 
 * @throws ProductOperationException
 */
	ProductExecution addProduct(Product product ,ImageHolder thumbnail,List<ImageHolder> productImgList) throws ProductOperationException;
/**
 * 通过商品查询唯一的商品信息
 * @param productId
 * @return
 */
	
	Product getProductById(long productId);
	
/**
 * 
 * @param ProductCondition
 * @param pageIndex
 * @param pageSize
 * @return
 */
	ProductExecution getProductList(Product ProductCondition,int pageIndex,int pageSize);
	

/**
 * 
 * @param product
 * @param thumbnail
 * @param productImgList
 * @return
 * @throws ProductOperationException
 */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgList)throws ProductOperationException;
}
