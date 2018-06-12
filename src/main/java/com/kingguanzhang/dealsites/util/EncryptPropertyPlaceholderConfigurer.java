package com.kingguanzhang.dealsites.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * //自定义的PlaceholderConfigurer,需要继承PropertyPlaceholderConfigurer,而PropertyPlaceholder就是原先不采用加密db文件时,配置文件里面用于读取db文件的标签;
 */
public class EncryptPropertyPlaceholderConfigurer extends
        PropertyPlaceholderConfigurer {
	//需要加密的字段
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			//对已经加密的字段进行解密
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}

	//判断是否是已经加密的字段;
	private boolean isEncryptProp(String propertyName) {
		for (String encryptpropertyName : encryptPropNames) {
			if (encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}
}
