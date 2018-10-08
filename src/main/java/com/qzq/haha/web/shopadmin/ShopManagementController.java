package com.qzq.haha.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock.Catch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzq.haha.dto.ImageHolder;
import com.qzq.haha.dto.ShopExecution;
import com.qzq.haha.entity.Area;
import com.qzq.haha.entity.PersonInfo;
import com.qzq.haha.entity.Shop;
import com.qzq.haha.entity.ShopCategory;
import com.qzq.haha.enums.ShopStateEnum;
import com.qzq.haha.service.AreaService;
import com.qzq.haha.service.ShopCategoryService;
import com.qzq.haha.service.ShopService;
import com.qzq.haha.util.CodeUtil;
import com.qzq.haha.util.HttpServletRequestUtil;
import com.qzq.haha.util.ImageUtil;
import com.qzq.haha.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value="/getshopmanagementinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
	Map<String, Object>	 modelMap = new HashMap<String,Object>();
	long shopId =HttpServletRequestUtil.getLong(request, "shopId");
	if(shopId<=0){
		Object currentShopObj = request.getSession().getAttribute("currentShop");
		if(currentShopObj==null){
			modelMap.put("redirect", true);
			modelMap.put("url", "/o2oCity/shopadmin/shoplist");
		}else{
			Shop currentShop = (Shop)currentShopObj;
			request.getSession().setAttribute("currentShop",currentShopObj);
			modelMap.put("redirect", false);
			modelMap.put("shopId", currentShop.getShopId());
			
		}
	}else{
		Shop currentShop = new Shop();
		currentShop.setShopId(shopId);
		request.getSession().setAttribute("currentShop", currentShop);
		modelMap.put("redirect", false);
	}
	return modelMap;
	}
	
	@RequestMapping(value="/getshoplist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1l);
		user.setName("哈哈");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo)request.getSession().getAttribute("user");
		List<Shop> shopList = new ArrayList<Shop>();
		try{
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			
			ShopExecution sExecution = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList", sExecution.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		}catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		
		return modelMap;
	}
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId>-1){
			try{
			Shop shop = shopService.getByShopId(shopId);
			List<Area> areaList = areaService.getAreaList();
			List<ShopCategory> shopCategory= shopCategoryService.getShopCategoryList(new ShopCategory());
			modelMap.put("shop", shop);
			modelMap.put("areaList", areaList);
			modelMap.put("shopCategoryList", shopCategory);
			modelMap.put("success", true);
			return modelMap;
			}catch (Exception e) {
				// TODO: handle exception
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
       if(!CodeUtil.checkVerifyCode(request)){
    	   modelMap.put("success", false);
    	   modelMap.put("errMsg", "输入了错误的验证码");
    	   return modelMap;
       }
		String name =request.getQueryString();
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "没有信息");
		e.printStackTrace();
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
		request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} 
		//修改店铺信息
		if (shop != null && shop.getShopId()!=null) {
			PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");

		    shop.setOwner(owner);
		    File shopImgFile = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName());
		    try {
				shopImgFile.createNewFile();
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		    try {
		    	if(shopImg!=null)
				inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		    ShopExecution sExecution;
		    if(shopImg==null){
		    	sExecution = shopService.modifyShop(shop, null);
		    }else{
		    	ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg);
		    	sExecution = shopService.modifyShop(shop, imageHolder);
		    }
		
		    if(sExecution.getState()==ShopStateEnum.CHECK.getState()){
		    	modelMap.put("success", true);
		    }else{
		    	modelMap.put("success", false);
		    	modelMap.put("errMsg", sExecution.getStateInfo());
		    }
		    return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}

	}
	@RequestMapping(value="/getshopinitinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo(){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
	    List<Area> areaList = new ArrayList<Area>();
	    try{
	    shopCategoryList = shopCategoryService.getShopCategoryList(null);
	    areaList = areaService.getAreaList();
	    
	    modelMap.put("shopCategoryList", shopCategoryList);
	    modelMap.put("areaList", areaList);
	    modelMap.put("success", true);
	    }catch (Exception e) {
			// TODO: handle exception
	    	  modelMap.put("success", false);
	    	  modelMap.put("errMsg",e.getMessage());
		}
	    return modelMap;
	}
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
       if(!CodeUtil.checkVerifyCode(request)){
    	   modelMap.put("success", false);
    	   modelMap.put("errMsg", "输入了错误的验证码");
    	   return modelMap;
       }
		String name =request.getQueryString();
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "没有信息");
		e.printStackTrace();
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
		request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		if (shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
		
		    shop.setOwner(owner);
		    File shopImgFile = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName());
		    try {
				shopImgFile.createNewFile();
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		    try {
				inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		    ShopExecution sExecution = shopService.addShop(shop, shopImg);
		    if(sExecution.getState()==ShopStateEnum.CHECK.getState()){
		    	modelMap.put("success", true);
		    	List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
		    if(shopList==null||shopList.size()==0){
		    	shopList=new ArrayList<Shop>();
		    }
		    shopList.add(sExecution.getShop());
		    request.getSession().setAttribute("shopList", shopList);
		    
		    }else{
		    	modelMap.put("success", false);
		    	modelMap.put("errMsg", sExecution.getStateInfo());
		    }
		    return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}

	}
	private static void inputStreamToFile(InputStream ins,File file){
		OutputStream oStream =null;
		try{
		oStream = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer  = new byte[1024];
		while((bytesRead = ins.read(buffer))!=-1){
			oStream.write(buffer, 0, bytesRead);
		}
		}catch (Exception e) {
			// TODO: handle exception
		throw new RuntimeException("调用inputStreamToFile产生异常："+e.getMessage());
		}finally {
			try{
			if(oStream!=null){
				oStream.close();
			}
			if(ins!=null){
				ins.close();
			}
			}catch (IOException e) {
				// TODO: handle exception
			throw new RuntimeException("inputStreamToFile关闭io产生异常："+e.getMessage());
			
			}
		}
	}
}
