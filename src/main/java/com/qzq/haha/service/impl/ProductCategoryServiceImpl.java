package com.qzq.haha.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qzq.haha.dao.ProductCategoryDao;
import com.qzq.haha.dao.ProductDao;
import com.qzq.haha.dto.ProductCategoryExecution;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.enums.ProductCategoryStateEnum;
import com.qzq.haha.exceptions.ProductCategoryOperationException;
import com.qzq.haha.service.ProductCategoryService;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategory;
	@Autowired
	private ProductDao productDao;
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		// TODO Auto-generated method stub
		return productCategory.queryProductCategoryList(shopId);
	}
	@Override
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) 
			throws ProductCategoryOperationException {
		// TODO Auto-generated method stub
		if(productCategoryList!=null&&productCategoryList.size()!=0){
			try{
			int effectedNum = productCategory.batchInsertProductCategory(productCategoryList);
			if(effectedNum<=0){
				throw new ProductCategoryOperationException("店铺类别创建失败");
			}else{
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
			
			
			}catch (Exception e) {
				// TODO: handle exception
				throw new ProductCategoryOperationException("batchAddProductCategory error:"+e.getMessage());
			}
			
			
		}else{
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}
	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		// TODO Auto-generated method stub
		try{
			int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
			if(effectedNum<=0){
				throw new RuntimeException("商品类别更新失败");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		try{
			int effectedNum = productCategory.deleteProductCategory(productCategoryId, shopId);
			if(effectedNum<=0){
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else{
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
			
		}catch (ProductCategoryOperationException e) {
			// TODO: handle exception
			throw new ProductCategoryOperationException(e.getMessage());
		}
	
	}
}
