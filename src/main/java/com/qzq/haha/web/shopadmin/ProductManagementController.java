package com.qzq.haha.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzq.haha.dto.ImageHolder;
import com.qzq.haha.dto.ProductExecution;
import com.qzq.haha.entity.Product;
import com.qzq.haha.entity.ProductCategory;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.enums.ProductStateEnum;
import com.qzq.haha.exceptions.ProductOperationException;
import com.qzq.haha.service.ProductCategoryService;
import com.qzq.haha.service.ProductService;
import com.qzq.haha.util.CodeUtil;
import com.qzq.haha.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;

	private static final int IMAGEMAXCOUNT = 6;

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			boolean k = multipartResolver.isMultipart(request);
			if (multipartResolver.isMultipart(request)) {
				thumbnail=handleImage(request, thumbnail, productImgList);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			product = mapper.readValue(productStr, Product.class);

		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString() + "123");
			return modelMap;
		}
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				;
				product.setShop(shop);
				ProductExecution pExecution = productService.addProduct(product, thumbnail, productImgList);
				if (pExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pExecution.getStateInfo());
					
				}
			} catch (ProductOperationException e) {
				// TODO: handle exception
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else{
		modelMap.put("success", false);
		modelMap.put("errMsg", "图片上传不能为空");
		return modelMap;
		}
		return modelMap;
	}
	@RequestMapping(value="/getproductlistbyshop",method = RequestMethod.GET)
	@ResponseBody
    private Map<String, Object> getProductListByShop(HttpServletRequest request){
    	Map<String, Object> modelMap = new HashMap<String,Object>();
    	int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
    	int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
    	Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
    	if((pageIndex>-1)&&(pageSize>-1)&&(currentShop!=null)&&(currentShop.getShopId()!=null)){
    		long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
    		String productName = HttpServletRequestUtil.getString(request, "productName");
    		Product productCondition = compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
    		ProductExecution peExecution = productService.getProductList(productCondition, pageIndex, pageSize);
    	    modelMap.put("productList", peExecution.getProductList());
    	    modelMap.put("count", peExecution.getCount());
    	    modelMap.put("success", true);    
    	}else{
    		modelMap.put("success", false);
    		modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
    	}
    	return modelMap;
    }
	private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
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
		return productCondition;
	}
	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductById(@RequestParam Long productId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productId > -1) {
			Product product = productService.getProductById(productId);
			List<ProductCategory> productCategoryLists = productCategoryService
					.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryLists);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入错误的验证码");
			return modelMap;
		}

		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			if (multipartResolver.isMultipart(request)) {
				thumbnail=handleImage(request, thumbnail, productImgList);
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		
		try{
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			product = mapper.readValue(productStr,Product.class);
		}catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if(product!=null){
			try{
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				
				ProductExecution pExecution =productService.modifyProduct(product, thumbnail, productImgList);
				if(pExecution.getState()==ProductStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);	
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", pExecution.getStateInfo());
				}
			}catch (ProductOperationException e) {
				// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}

	private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList) {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest
				.getFile("thumbnail");
		if (thumbnailFile != null) {
			thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile);
		}
		for (int i = 0; i < IMAGEMAXCOUNT; i++) {
			CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest
					.getFile("productImg" + i);
			if (productImgFile != null) {
				ImageHolder productimg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile);
				productImgList.add(productimg);
			} else {
				break;
			}
		}
		return thumbnail;
	}
}
