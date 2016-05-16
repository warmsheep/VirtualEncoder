package org.warmsheep.encoder.bean;

import java.io.Serializable;

public class CommandBean implements Serializable{
	
	//MS指令相关
	public static final int MSG_BLOCK_LENGTH = 1;			//报文块标志域长度
	public static final int KEY_TYPE_LENGTH = 1;			//密钥类型域长度
	public static final int KEY_LENGTH_TYPE_LENGTH = 1;		//密钥长度类型域长度
	public static final int MSG_TYPE_LENGTH = 1;			//数据类型域长度
	
	public static final int DOUBLE_KEY_ADD_ONE_LENGTH = 33;		//双倍长密钥域长度33（带X）
	public static final int DOUBLE_KEY_LENGTH = 32;		//双倍长密钥域长度32（不带X）
	public static final int SINGLE_KEY_LENGTH = 16;		//单倍长密钥域长度
	
	public static final int DATA_LENGTH = 4;			//数据长度域长度
	
	//CC指令相关
	public static final int MAX_PIN_LENGTH = 2;		//最大PIN长度域长度
	public static final int SOURCE_PIN_BLOCK_ENCRYPT_DATA_LENGTH = 16;	//源PIN BLOCK密文数据域长度
	public static final int SOURCE_PIN_BLOCK_FORMAT_LENGTH = 2;			//源PIN BLOCK数据格式域长度
	public static final int TARGET_PIN_BLOCK_FORMAT_LENGTH = 2;			//目标PIN BLOCK数据格式域长度
	
	public static final int ACCOUNT_NO_LENGTH = 12;		//用户主账号长度

	private String commandHeader;		//命令头
	private String commandType;			//命令类型
	/**
	 * 命令头
	 * @return
	 */
	public String getCommandHeader() {
		return commandHeader;
	}
	/**
	 * 命令头
	 * @param commandHeader
	 */
	public void setCommandHeader(String commandHeader) {
		this.commandHeader = commandHeader;
	}
	/**
	 * 命令类型
	 * @return
	 */
	public String getCommandType() {
		return commandType;
	}
	/**
	 * 命令类型
	 * @param commandType
	 */
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
}
