package com.qzq.haha.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qzq.haha.dao.ProductDao;
import com.qzq.haha.dao.ProductImgDao;
import com.qzq.haha.dto.ImageHolder;
import com.qzq.haha.dto.ProductExecution;
import com.qzq.haha.dto.ShopExecution;
import com.qzq.haha.entity.Product;
import com.qzq.haha.entity.ProductImg;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.enums.ProductStateEnum;
import com.qzq.haha.exceptions.ProductOperationException;
import com.qzq.haha.service.ProductService;
import com.qzq.haha.util.ImageUtil;
import com.qzq.haha.util.PageCalculator;
import com.qzq.haha.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao ProductImgDao;

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		// TODO Auto-generated method stub
		if(product!=null && product.getShop()!=null &&product.getShop().getShopId()!=null){
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(1);
			if(thumbnail!=null){
				addThumbnail(product,thumbnail);
			}
			try{
				int effectedNum = productDao.insertProduct(product);
				if(effectedNum<=0){
					throw new ProductOperationException("创建商品失败");
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				throw new ProductOperationException("创建商品失败:"+e.toString());
			}
			
			if(productImgList!=null&&productImgList.size()>0){
			addProductImgList(product,productImgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
			
		}else{
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	@Transactional
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		// TODO Auto-generated method stub
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail.getImage(), dest);
		product.setImgAddr(thumbnailAddr);
	}
	
	@Transactional
	private void addProductImgList(Product product, List<ImageHolder> productImgList) {
		// TODO Auto-generated method stub
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImglist = new ArrayList<ProductImg>();
		for(ImageHolder productImgHolder : productImgList){
			String imgAddr = ImageUtil.generateThumbnail(productImgHolder.getImage(), dest);
		    ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImglist.add(productImg);
		}
		
		if(productImgList.size()>0){
			try{
				int effectedNum = ProductImgDao.batchInsertProductImg(productImglist);
				if(effectedNum<=0){
					throw new ProductOperationException("创建商品详情图片失败");
				}
			}catch (Exception e) {
				// TODO: handle exception
				throw new ProductOperationException("创建商品详情失败："+e.toString());
			}
		}
	}

	@Override
	@Transactional
	public Product getProductById(long productId) {
		// TODO Auto-generated method stub
		return productDao.queryProductByProductId(productId);
	}

	@Override
	@Transactional
	public ProductExecution getProductList(Product ProductCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(ProductCondition, rowIndex, pageSize) ;
		int count = productDao.queryProductCount(ProductCondition);
		ProductExecution pExecution=new ProductExecution();
		pExecution.setCount(count);
		pExecution.setProductList(productList);
		return pExecution;
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		// TODO Auto-generated method stub
		//控制判断
		if(product!=null && product.getShop()!=null &&product.getShop().getShopId()!=null){
			//覆上默认值
			
			product.setLastEditTime(new Date());
			if(thumbnail!=null){
				Product tempProduct = productDao.queryProductByProductId(product.getProductId());
				if(tempProduct.getImgAddr()!=null){
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product,thumbnail);
				
			}
			
			if(productImgList!=null&&productImgList.size()>0){
				deleteProductImgList(product.getProductId());
			    addProductImgList(product,productImgList);
			}
			try{
				int effectedNum = productDao.updateProduct(product);
				if(effectedNum<=0){
					throw new ProductOperationException("创建商品失败");
				}

				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			}catch (Exception e) {
				// TODO: handle exception
				throw new ProductOperationException("创建商品失败:"+e.toString());
			}
			
			
			
			
		}else{
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	private void deleteProductImgList(Long productId) {
		// TODO Auto-generated method stub
		List<ProductImg> productImgs = ProductImgDao.queryProductImgList(productId);
		for(ProductImg img :productImgs){
			ImageUtil.deleteFileOrPath(img.getImgAddr());
		}
		ProductImgDao.deleteProductImgByProductId(productId);
	}



}
