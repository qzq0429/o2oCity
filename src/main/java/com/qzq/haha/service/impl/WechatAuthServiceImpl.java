package com.qzq.haha.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.log.Log;
import com.qzq.haha.dao.PersonInfoDao;
import com.qzq.haha.dao.WechatAuthDao;
import com.qzq.haha.dto.WechatAuthExecution;
import com.qzq.haha.entity.PersonInfo;
import com.qzq.haha.entity.WechatAuth;
import com.qzq.haha.enums.WechatAuthStateEnum;
import com.qzq.haha.exceptions.WechatAuthOperationException;
import com.qzq.haha.service.WechatAuthService;

import java.util.Date;

import org.slf4j.Logger;;;
@Service
public class WechatAuthServiceImpl implements WechatAuthService {
    private static Logger Log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);
     
    @Autowired
    private WechatAuthDao WechatAuthDao;
    @Autowired
    private PersonInfoDao personInfoDao;
	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		// TODO Auto-generated method stub
		return WechatAuthDao.queryWechatInfoByOpenId(openId);
	}

	@Override
	public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
		// TODO Auto-generated method stub
		if(wechatAuth ==null&&wechatAuth.getPersonInfo().getUserId()==null){
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}
		try{
			wechatAuth.setCreateTime(new Date());
			if(wechatAuth.getPersonInfo()!=null&&wechatAuth.getPersonInfo().getUserId()==null){
				
				
				try{
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					
					int effectedNum = personInfoDao.insertPersonInfo(personInfo);
					wechatAuth.setPersonInfo(personInfo);
					if(effectedNum<=0){
						throw new WechatAuthOperationException("添加用户信息失败");
					}
					
				}catch (Exception e) {
					// TODO: handle exception
					Log.error("insertPersosn error:"+e.toString());
					throw new WechatAuthOperationException("insertPersosn error:"+e.toString());
				}
			}
			
			int effectedNum = WechatAuthDao.insertWechatAuth(wechatAuth);
			if(effectedNum<=0){
				throw new WechatAuthOperationException("账号创建失败");
				
			}else{
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,wechatAuth);
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
			Log.error(e.toString());
			throw new WechatAuthOperationException(e.toString());
			
		}
		
	}

}
