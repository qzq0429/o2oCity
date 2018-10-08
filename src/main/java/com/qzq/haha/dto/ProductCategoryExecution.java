package com.qzq.haha.dto;

import java.util.List;

import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {

	private int state;
	
	private String stateInfo;
	
	private List<ProductCategory> productCategories;
	public ProductCategoryExecution(){
		
	}
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum ){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> list ){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategories = list;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public List<ProductCategory> getProductCategories() {
		return productCategories;
	}
	public void setProductCategories(List<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}
	
}
