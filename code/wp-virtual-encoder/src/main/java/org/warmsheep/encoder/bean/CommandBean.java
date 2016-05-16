package org.warmsheep.encoder.bean;

import java.io.Serializable;

public class CommandBean implements Serializable{
	
	public static final int MSG_BLOCK_LENGTH = 1;			//报文块标志域长度
	public static final int KEY_TYPE_LENGTH = 1;			//密钥类型域长度
	public static final int KEY_LENGTH_TYPE_LENGTH = 1;		//密钥长度类型域长度
	public static final int MSG_TYPE_LENGTH = 1;			//数据类型域长度
	
	public static final int DOUBLE_KEY_LENGTH = 33;		//双倍长密钥域长度33（带X）
	public static final int SINGLE_KEY_LENGTH = 16;		//单倍长密钥域长度
	
	public static final int DATA_LENGTH = 4;			//数据长度域长度

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
