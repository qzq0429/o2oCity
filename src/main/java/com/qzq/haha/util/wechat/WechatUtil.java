package com.qzq.haha.util.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzq.haha.dto.UserAccessToken;
import com.qzq.haha.dto.WechatUser;
import com.qzq.haha.entity.PersonInfo;

public class WechatUtil {
       private static Logger log = LoggerFactory.getLogger(WechatUtil.class);

       public static UserAccessToken getUserAccessToken(String code) throws IOException{
    	   String appId = "wxfce38583a54e864e";
    	   log.debug("appId:"+appId);
    	   String appsecret = "a37052af4a6e42ef2baf36d017e7e170";
    	   String url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret 
    			   + "&code=" + code + "&grant_type=authorization_code";
    	   String tokenStr = httpsRequest(url, "GET", null);
    	   log.debug("userAccessToken:"+tokenStr); 
           UserAccessToken token = new UserAccessToken();
           ObjectMapper objectMapper = new ObjectMapper();
           try{
        	   token = objectMapper.readValue(tokenStr, UserAccessToken.class);
           }catch (Exception e) {
			// TODO: handle exception
        	   log.error("获取用户accessToken失败: " + e.getMessage());
               e.printStackTrace();
           }
           if(token == null){
        	   log.error("获取用户accessToken失败。");
        	   return null;
           }
           return token;
       }
       
       public static WechatUser getUserInfo(String accessToken,String openId){
    	   String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId 
    			   + "&lang=zh_CN";
    	   
    	   String userStr = httpsRequest(url, "GET", null);
    	   
    	   log.debug("user info :"+userStr);
    	   
    	   WechatUser user = new WechatUser();
    	   
    	   ObjectMapper objectMapper = new ObjectMapper();
    	   
    	   try{
    		   user = objectMapper.readValue(userStr, WechatUser.class);
    	   }catch (Exception e) {
			// TODO: handle exception
    		   log.error("获取用户accessToken失败: " + e.getMessage());
               e.printStackTrace();
		}
    	   if(user == null){
    		   log.error("获取用户信息失败");
    		   return null;
    	   }
    	   return user;
    	   
       }
       public static PersonInfo getPersonInfoFromRequest(WechatUser user){
    	   PersonInfo personInfo = new PersonInfo();
    	   personInfo.setName(user.getNickname());
    	   personInfo.setGender(user.getSex()+"");
    	   personInfo.setProfileImg(user.getHeadimgurl());
    	   personInfo.setEnableStatus(1);
    	   return personInfo;
       }
       
       
       public static String httpsRequest(String requestUrl,String requestMethod,
    		   String outputStr){
    	   StringBuffer buffer = new StringBuffer();
    	   try {
			
    		   TrustManager[] tm = {new MyX509TrustManager()};
    		   SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
    		   sslContext.init(null, tm, new java.security.SecureRandom());
    		   SSLSocketFactory ssFactory = sslContext.getSocketFactory();
    		   
    		   URL url = new URL(requestUrl);
    		   HttpsURLConnection httpsURLConn = (HttpsURLConnection)url.openConnection();
    		   httpsURLConn.setSSLSocketFactory(ssFactory);
    		   
    		   httpsURLConn.setDoInput(true);
    		   httpsURLConn.setDoOutput(true);
    		   httpsURLConn.setUseCaches(false);
    		   httpsURLConn.setRequestMethod(requestMethod);
    		   
    		   if("GET".equalsIgnoreCase(requestMethod)){
    			   httpsURLConn.connect();
    		   }
    		   
    		   if(null != outputStr){
    			   OutputStream outputStream = httpsURLConn.getOutputStream();
    			   
    			   outputStream.write(outputStr.getBytes("UTF-8"));
    			   outputStream.close();
    		   }
    		   
    		   InputStream inputStream = httpsURLConn.getInputStream();
    		   InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
    		   BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    		   
    		   String str =null;
    		   while((str = bufferedReader.readLine())!= null){
    			   buffer.append(str);
    		   }
    		   bufferedReader.close();
    		   inputStreamReader.close();
    		   httpsURLConn.disconnect();
    		   log.debug("https buffer:"+buffer.toString());
		   } catch (Exception e) {
			// TODO: handle exception
			   log.error(e.getMessage());
		   }
    	   
    	   return buffer.toString();
       }
}
