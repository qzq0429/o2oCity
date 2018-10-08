package com.qzq.haha.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.CompactStringObjectMap;
import com.qzq.haha.dto.ProductExecution;
import com.qzq.haha.entity.Area;
import com.qzq.haha.entity.Product;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.entity.ShopCategory;
import com.qzq.haha.service.ProductCategoryService;
import com.qzq.haha.service.ProductService;
import com.qzq.haha.service.ShopService;
import com.qzq.haha.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
  @Autowired
  private ShopService shopService;
  @Autowired
  private ProductService productService;
  @Autowired
  private ProductCategoryService productCategoryService;
  
  /**
   * 获取店铺信息以及该店铺下面的商品类别
   * 
   * @param request
   * @return
   */
  
  @RequestMapping(value="/listshopdetailpageinfo",method=RequestMethod.GET)
  @ResponseBody
  private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request){
	  Map<String, Object> modelMap = new HashMap<String,Object>();
	  long shopId = HttpServletRequestUtil.getLong(request, "shopId");
	  Shop shop = null;
	  List<ProductCategory> productCategoryList = null;
	  if(shopId!=-1){
		  shop = shopService.getByShopId(shopId);
		  productCategoryList = productCategoryService.getProductCategoryList(shopId);
		  modelMap.put("shop", shop);
		  modelMap.put("productCategoryList", productCategoryList);
		  modelMap.put("success", true);
	  }else{
		  modelMap.put("success", false);
		  modelMap.put("errMsg", "empty shopId");
	  }
	  return modelMap;
  }
  @RequestMapping(value="/listproductsbyshop",method=RequestMethod.GET)
  @ResponseBody
  private Map<String, Object> listProductsByShop(HttpServletRequest request){
	  Map<String, Object> modelMap = new HashMap<String,Object>();
	  int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
	  int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
	  long shopId = HttpServletRequestUtil.getLong(request, "shopId");
	  if((pageIndex>-1)&&(pageSize>-1)&&(shopId>-1l)){
		  long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
		  
		  String productName = HttpServletRequestUtil.getString(request, "productName");
	  
		  Product productCondition = compactProductCondition4Search(shopId,productCategoryId,productName);
	  
		  ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
	      
		 
		  modelMap.put("count", pe.getCount());
		  modelMap.put("productList", pe.getProductList());
		  modelMap.put("success", true);
	  }else{
		  modelMap.put("success", false);
		  modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		  
	  }
	  
	  return modelMap;
  }
private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
	// TODO Auto-generated method stub
	Product productCondition = new Product();
	Shop shop = new Shop();
	shop.setShopId(shopId);
	productCondition.setShop(shop);
	if(productCategoryId!=-1l){
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(productCategoryId);
		productCondition.setProductCategory(productCategory);
	}
	if(productName!=null){
		productCondition.setProductName(productName);
	}
	
	productCondition.setEnableStatus(1);
	return productCondition;
}
	
  

}
