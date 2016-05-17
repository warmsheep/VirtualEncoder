package org.warmsheep.encoder.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 密钥类型
 * @author Warmsheep
 *
 */
public enum KeyLengthType {

	/**
	 * 1、双倍长
	 */
	DOUBLE_LENGTH("1", "双倍长"),
	/**
	 * 0、单倍长
	 */
	SINGLE_LENGTH("0", "单倍长");
	
	
	
	private String value;
	private String key;

	private KeyLengthType(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * 通过下标获得枚举
	 */
	public static KeyLengthType getByIndex(Integer index) {
		if (null == index)
			return null;
		for (KeyLengthType at : KeyLengthType.values()) {
			if (at.key.equals(index))
				return at;
		}
		return null;
	}

	/**
	 * 通过名称获得枚举
	 */
	public static KeyLengthType getByValue(String value) {
		if (StringUtils.isBlank(value))
			return null;
		for (KeyLengthType at : KeyLengthType.values()) {
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
