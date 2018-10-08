package com.qzq.haha.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qzq.haha.dto.ProductCategoryExecution;
import com.qzq.haha.dto.Result;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.enums.ProductCategoryStateEnum;
import com.qzq.haha.exceptions.ProductCategoryOperationException;
import com.qzq.haha.exceptions.ShopOperationException;
import com.qzq.haha.service.ProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {

		Shop shop = new Shop();
		shop.setShopId(2l);
		request.getSession().setAttribute("currentShop", shop);

		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			Result<List<ProductCategory>> sResult = new Result<List<ProductCategory>>(true, list);
			return sResult;
		} else {
			ProductCategoryStateEnum pStateEnum = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false, pStateEnum.getState(), pStateEnum.getStateInfo());
		}
	}

	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		if (productCategoryList != null && productCategoryList.size() != 0) {
			try {
				ProductCategoryExecution pExecution = productCategoryService
						.batchAddProductCategory(productCategoryList);
				if (pExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pExecution.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				// TODO: handle exception
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");

		}
		return modelMap;
	}
	@RequestMapping(value="/removeproductcategory",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		if(productCategoryId!=null&&productCategoryId>0){
			try{
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pExecution = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pExecution.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
				}else{
					modelMap.put("success", true);
					modelMap.put("errMsg", pExecution.getStateInfo());
				}
			}catch (ShopOperationException e) {
				// TODO: handle exception
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else{
			modelMap.put("success", true);
			modelMap.put("errMsg", "请至少选择一个商品类别");
		}
		return modelMap;
	}
}
