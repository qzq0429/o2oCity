package com.qzq.haha.enums;

public enum ShopStateEnum {
	
CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),
SUCCESS(1,"操作成功"),PASS(2,"通过验证"),
INNER_ERROR(1001,"内部系统错误"),NULL_SHOPID(-1002,"ShopId为空"),
NULL_SHOP(-1003,"shop信息为空");

private int state;
private String stateInfo;


private ShopStateEnum(int state,String stateInfo) {
	this.state=state;
	this.stateInfo=stateInfo;
}


public static ShopStateEnum stateOf(int state){
	for(ShopStateEnum stateEnum : values()){
		if(stateEnum.getState()==state)
			return stateEnum;
	}
	return null;
}


public int getState() {
	return state;
}


public String getStateInfo() {
	return stateInfo;
}


public void setState(int state) {
	this.state = state;
}


public void setStateInfo(String stateInfo) {
	this.stateInfo = stateInfo;
}

}
