package com.qzq.haha.dto;

import java.util.List;

import com.qzq.haha.entity.Product;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.enums.ProductStateEnum;
import com.qzq.haha.enums.ShopStateEnum;

public class ProductExecution {
	//结果状态
		private int state;
	//状态标识	
		private String stateInfo;
	//店铺数量
		private int count;
	//操作店铺用到
		private Product product;
	//shop列表（查询店铺列表）
		private List<Product> productList;
		public ProductExecution(){
			
		}
		/**
		 * 店铺操作失败的时候使用的构造器
		 * @param stateEnum
		 */
		public ProductExecution(ProductStateEnum stateEnum){
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}
		/**
		 * 店铺操作成功的时候使用的构造器
		 * @param stateEnum
		 * @param shop
		 */
		public ProductExecution(ProductStateEnum stateEnum,Product product){
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.product = product;
		}
		/**
		 * 店铺操作成功的时候使用的构造器
		 * @param stateEnum
		 * @param shopList
		 */
		public ProductExecution(ProductStateEnum stateEnum,List<Product> productList){
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.productList = productList;
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
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public List<Product> getProductList() {
			return productList;
		}
		public void setProductList(List<Product> productList) {
			this.productList = productList;
		}
}
