package com.qzq.haha.dto;

import java.util.List;

import com.qzq.haha.entity.WechatAuth;
import com.qzq.haha.enums.WechatAuthStateEnum;
import com.qzq.haha.exceptions.WechatAuthOperationException;

public class WechatAuthExecution {

	private int state;
	
	private String stateInfo;
	
	private int count;
	
	private WechatAuth wechatAuth;
	
	private List<WechatAuth> wechatAuthList;
	
	public WechatAuthExecution(){
		
	}
	
	public WechatAuthExecution(WechatAuthStateEnum stateEnum){
		
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	public WechatAuthExecution(WechatAuthStateEnum stateEnum,WechatAuth wechatAuth) throws WechatAuthOperationException{
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.wechatAuth = wechatAuth;
	}
	public WechatAuthExecution(WechatAuthStateEnum stateEnum,List<WechatAuth> wechatAuthList) throws WechatAuthOperationException{
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.wechatAuthList = wechatAuthList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public WechatAuth getWechatAuth() {
		return wechatAuth;
	}

	public void setWechatAuth(WechatAuth wechatAuth) {
		this.wechatAuth = wechatAuth;
	}

	public List<WechatAuth> getWechatAuthList() {
		return wechatAuthList;
	}

	public void setWechatAuthList(List<WechatAuth> wechatAuthList) {
		this.wechatAuthList = wechatAuthList;
	}
	
}
