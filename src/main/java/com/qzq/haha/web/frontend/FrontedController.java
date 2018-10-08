package com.qzq.haha.web.frontend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qzq.haha.entity.PersonInfo;

@Controller
@RequestMapping("/frontend")
public class FrontedController {
	@RequestMapping(value="/index")
	public String index(){
		return "/frontend/index";
	}
	@RequestMapping(value="/shoplist")
	public String shoplist(){
		return "/frontend/shoplist";
	}
	@RequestMapping(value="/shopdetail")
	public String shopdetail(){
		return "/frontend/shopdetail";
	}
	@RequestMapping(value="/xixi")
	public String test(HttpServletRequest request){
		PersonInfo personInfo = new PersonInfo();
		personInfo.setProfileImg("http://thirdwx.qlogo.cn/mmopen/vi_32/C1sibqIn3C3bqHZCa35GLyADazJiaL4Yp2eQic2SXKuok69I61zmRQFBfcjbMHC1qZRa0J7M9LyjAXaFj1sE7zduw/132");
		personInfo.setName("Qzq");
		request.getSession().setAttribute("user", personInfo);
		return "/frontend/index";
	}
}
