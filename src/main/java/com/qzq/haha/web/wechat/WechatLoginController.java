package com.qzq.haha.web.wechat;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.proto.xmlrpc.AuthenticatedXmlRpcMethodCaller;
import com.qzq.haha.dto.UserAccessToken;
import com.qzq.haha.dto.WechatAuthExecution;
import com.qzq.haha.dto.WechatUser;
import com.qzq.haha.entity.PersonInfo;
import com.qzq.haha.entity.WechatAuth;
import com.qzq.haha.enums.WechatAuthStateEnum;
import com.qzq.haha.service.PersonInfoService;
import com.qzq.haha.service.WechatAuthService;
import com.qzq.haha.util.wechat.WechatUtil;

@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {

	
	private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);
	
	@Autowired
	private PersonInfoService PersonInfoService;
	@Autowired
	private WechatAuthService wechatAuthService;
	
	private static final String FRONTEND="1";
	private static final String SHOPEND="2";
	
	@RequestMapping(value="/logincheck",method={RequestMethod.GET})
	public String doGet(HttpServletRequest request,HttpServletResponse response){
		log.debug("weinxin login get....");
		
		String code = request.getParameter("code");
		String roleType = request.getParameter("state");
		log.debug("weixin login code:"+code);
		WechatUser user = null;
		String openId = null;
		WechatAuth auth=null;
		if(null != code){
			UserAccessToken token;
			try {
				token = WechatUtil.getUserAccessToken(code);
				log.debug("weixin login token:"+token.toString());
				String accessToken = token.getAccessToken();
				openId = token.getOpenId();
				user = WechatUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:"+user.toString());
				request.getSession().setAttribute("openId", openId);
				auth=wechatAuthService.getWechatAuthByOpenId(openId);
				
			} catch (Exception e) {
				// TODO: handle exception
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId:"+e.toString());
                e.printStackTrace();
			}
		
		if(auth == null){
			PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
			auth = new WechatAuth();
			auth.setOpenId(openId);
			if(FRONTEND.equals(roleType)){
				personInfo.setUserType(1);
			}else{
				personInfo.setUserType(2);
			}
			auth.setPersonInfo(personInfo);
			
			WechatAuthExecution wechatAuthExecution = wechatAuthService.register(auth);
		    if(wechatAuthExecution.getState()!=WechatAuthStateEnum.SUCCESS.getState()){
		    	return null;
		    }else{
		    	personInfo = PersonInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
		    	request.getSession().setAttribute("user", personInfo);

		    }
		}else{
	    	PersonInfo person = PersonInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
	    	request.getSession().setAttribute("user", person);
		}
		
		}
		if(FRONTEND.equals(roleType)){
			return "/frontend/index";
		}else{
				return  "/shopadmin/shoplist";
			}
		}
	
    @RequestMapping(value="/userInfo",method={RequestMethod.GET})
    @ResponseBody
    public HashMap<String, Object> getuserInfo(HttpServletRequest request){
    	PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
    	HashMap<String, Object> model = new HashMap<String, Object>();
    	model.put("user", personInfo);
    	return model;
    }
	
}
