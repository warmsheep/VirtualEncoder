package org.warmsheep.encoder.bean;

import java.io.Serializable;

public class CommandBean implements Serializable{
	
	public static final int MSG_BLOCK_LENGTH = 1;
	public static final int KEY_TYPE_LENGTH = 1;
	public static final int KEY_LENGTH_TYPE_LENGTH = 1;
	public static final int MSG_TYPE_LENGTH = 1;
	
	public static final int DOUBLE_KEY_LENGTH = 33;
	public static final int SINGLE_KEY_LENGTH = 16;
	
	public static final int DATA_LENGTH = 4;

	private String commandHeader;
	private String commandType;
	public String getCommandHeader() {
		return commandHeader;
	}
	public void setCommandHeader(String commandHeader) {
		this.commandHeader = commandHeader;
	}
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
}
