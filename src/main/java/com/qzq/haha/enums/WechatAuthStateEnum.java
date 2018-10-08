package com.qzq.haha.enums;

public enum WechatAuthStateEnum {
	lOGINFAIL(-1,"openId输入错误"),
	SUCCESS(0,"操作成功"),
	NULL_AUTH_INFO(1,"openId为空");
	
	private int state;
	private String stateInfo;
	
	private WechatAuthStateEnum(int state,String stateInfo){
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public static WechatAuthStateEnum stateOf(int state){
		for(WechatAuthStateEnum stateEnum : values()){
			if(stateEnum.getState()==state)
				return stateEnum;
		}
		return null;
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
	
}
