package com.qzq.haha.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/shop",method={RequestMethod.GET})
public class ShopAdminController {
	@RequestMapping("/shopoperation")
	public String shopOpertion(){
		return "/shop/shopoperation";
	}
	@RequestMapping("/shoplist")
	public String shoplist(){
		return "/shop/shoplist";
	}
	@RequestMapping("/shopmanagement")
	public String shopmanage(){
		return "/shop/shopmanagement";
	}
	@RequestMapping("/productcategorymanagement")
	public String productcategorymanagement(){
		return "/shop/productcategorymanagement";
	}
	@RequestMapping("/productoperation")
	public String productoperation(){
		return "/shop/productoperation";
	}
	@RequestMapping("/productmanagement")
	public String productmanagement(){
		return "/shop/productmanagement";
	}
}
