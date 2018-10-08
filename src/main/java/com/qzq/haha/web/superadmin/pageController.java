package com.qzq.haha.web.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class pageController {

	@RequestMapping("login")
	public String web(){
		return "index";
	}
}
