package com.qzq.haha.util;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private String[] encryptPropNames = {"jdbc.username","jdbc.password"};
/**
 * 对关键的属性进行转换
 * @param propertyName
 * @param propertyValue
 * @return
 */
@Override
protected String convertProperty(String propertyName, String propertyValue) {
	// TODO Auto-generated method stub
			if(isEncryptProp(propertyName)){
				String decryptValue = DESUtils.getDecryptString(propertyValue);
				return decryptValue;
			}else{
				return propertyValue;
			}
}
    
	private boolean isEncryptProp(String propertyName){
		for(String encryptpropertyName : encryptPropNames){
			if(encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}

	
}
