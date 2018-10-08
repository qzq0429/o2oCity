package com.qzq.haha.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WechatUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8616007928347057818L;

	@JsonProperty("openid")
	private String openid;
	
	@JsonProperty("nickname")
	private String nickname;
	
	@JsonProperty("sex")
	private int sex;
	
	@JsonProperty("province")
	private String province;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("headimgurl")
	private String headimgurl;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("privilege")
	private String[] privilege;


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String[] getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
