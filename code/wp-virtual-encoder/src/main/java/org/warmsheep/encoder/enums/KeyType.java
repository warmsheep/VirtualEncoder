package org.warmsheep.encoder.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 密钥类型
 * @author Warmsheep
 *
 */
public enum KeyType {

	/**
	 * 1、区域密钥
	 */
	ZAK("1", "区域密钥"),
	/**
	 * 0、终端密钥
	 */
	TAK("0", "终端密钥");
	
	
	
	private String value;
	private String key;

	private KeyType(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * 通过下标获得枚举
	 */
	public static KeyType getByIndex(Integer index) {
		if (null == index)
			return null;
		for (KeyType at : KeyType.values()) {
			if (at.key.equals(index))
				return at;
		}
		return null;
	}

	/**
	 * 通过名称获得枚举
	 */
	public static KeyType getByValue(String value) {
		if (StringUtils.isBlank(value))
			return null;
		for (KeyType at : KeyType.values()) {
			if (at.value.equals(value))
				return at;
		}
		return null;
	}

	@Override
	public String toString() {
		return this.key + ":" + this.value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
