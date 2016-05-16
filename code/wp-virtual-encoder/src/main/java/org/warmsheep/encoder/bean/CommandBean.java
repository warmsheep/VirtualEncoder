package org.warmsheep.encoder.bean;

import java.io.Serializable;

public class CommandBean implements Serializable{
	
	
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
