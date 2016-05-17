package org.warmsheep.encoder.bean;

import org.warmsheep.encoder.enums.KeyLengthType;


public class MUCommandBean extends CommandBean{
	
	//MU指令相关
	private static final int MSG_BLOCK_LENGTH = 1;			//报文块标志域长度
	private static final int KEY_TYPE_LENGTH = 1;			//密钥类型域长度
	private static final int KEY_LENGTH_TYPE_LENGTH = 1;	//密钥长度类型域长度
	private static final int MSG_TYPE_LENGTH = 1;			//数据类型域长度
	
	private static final int DOUBLE_KEY_ADD_ONE_LENGTH = 33;//双倍长密钥域长度33（带X）
	private static final int DOUBLE_KEY_LENGTH = 32;		//双倍长密钥域长度32（不带X）
	private static final int SINGLE_KEY_LENGTH = 16;		//单倍长密钥域长度
	
	public static MUCommandBean build(String header,String commandType,String commandContent){
		MUCommandBean muCommandBean = new MUCommandBean();
		muCommandBean.setCommandHeader(header);
		muCommandBean.setCommandType(commandType);
		
		int subIndex = 0;
		
		muCommandBean.setMsgBlock(commandContent.substring(subIndex, subIndex += MSG_BLOCK_LENGTH));
		muCommandBean.setKeyType(commandContent.substring(subIndex, subIndex += KEY_TYPE_LENGTH));
		muCommandBean.setKeyLengthType(commandContent.substring(subIndex, subIndex += KEY_LENGTH_TYPE_LENGTH));
		muCommandBean.setMsgType(commandContent.substring(subIndex, subIndex += MSG_TYPE_LENGTH));
		
		if(muCommandBean.getKeyLengthType().equals(KeyLengthType.DOUBLE_LENGTH.getKey())){
			//双倍长密钥，以X开头
			if(commandContent.substring(subIndex,subIndex + 1).equalsIgnoreCase("X")){
				muCommandBean.setKeyValue(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_ADD_ONE_LENGTH));
			} 
			//双倍长密钥，标准长度
			else {
				muCommandBean.setKeyValue(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_LENGTH));
			}
		}
		//单倍长密钥
		else {
			muCommandBean.setKeyValue(commandContent.substring(subIndex, subIndex += SINGLE_KEY_LENGTH));
		}
		muCommandBean.setEncryptDataLength(Integer.valueOf(commandContent.substring(subIndex, subIndex += DATA_LENGTH), 16).toString());
		muCommandBean.setEncryptDataValue(commandContent.substring(subIndex, subIndex+= (Integer.valueOf(muCommandBean.getEncryptDataLength()) * 2) ) );
		
		return muCommandBean;
	}

	private String msgBlock;		//报文块标志 0唯一块 1第一块 2中间块 3最后块
	private String keyType;			//密钥类型 0TAK 1ZAK
	private String keyLengthType;	//密钥长度类型	0 8字节单倍长 1 16字节双倍长
	private String msgType;			//数据类型 0二进制 1扩展十六进制
	private String keyValue;		//密钥值
	private String encryptDataLength;		//待加密的数据长度
	private String encryptDataValue;		//待加密的数据
	
	/**
	 * 报文块标志 0唯一块 1第一块 2中间块 3最后块
	 * @return
	 */
	public String getMsgBlock() {
		return msgBlock;
	}
	/**
	 * 报文块标志 0唯一块 1第一块 2中间块 3最后块
	 * @param msgBlock
	 */
	public void setMsgBlock(String msgBlock) {
		this.msgBlock = msgBlock;
	}
	/**
	 * 密钥类型 0TAK 1ZAK
	 * @return
	 */
	public String getKeyType() {
		return keyType;
	}
	/**
	 * 密钥类型 0TAK 1ZAK
	 * @param keyType
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	/**
	 * 密钥长度类型	0 8字节单倍长 1 16字节双倍长
	 * @return
	 */
	public String getKeyLengthType() {
		return keyLengthType;
	}
	/**
	 * 密钥长度类型	0 8字节单倍长 1 16字节双倍长
	 * @param keyLengthType
	 */
	public void setKeyLengthType(String keyLengthType) {
		this.keyLengthType = keyLengthType;
	}
	/**
	 * 数据类型 0二进制 1扩展十六进制
	 * @return
	 */
	public String getMsgType() {
		return msgType;
	}
	/**
	 * 数据类型 0二进制 1扩展十六进制
	 * @param msgType
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/**
	 * 密钥值
	 * @return
	 */
	public String getKeyValue() {
		return keyValue;
	}
	/**
	 * 密钥值
	 * @param keyValue
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	/**
	 * 待加密的数据长度
	 * @return
	 */
	public String getEncryptDataLength() {
		return encryptDataLength;
	}
	/**
	 * 待加密的数据长度
	 * @param encryptDataLength
	 */
	public void setEncryptDataLength(String encryptDataLength) {
		this.encryptDataLength = encryptDataLength;
	}
	/**
	 * 待加密的数据
	 * @return
	 */
	public String getEncryptDataValue() {
		return encryptDataValue;
	}
	/**
	 * 待加密的数据
	 * @param encryptDataValue
	 */
	public void setEncryptDataValue(String encryptDataValue) {
		this.encryptDataValue = encryptDataValue;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[").append("CommandHeader").append("]:\t[").append("命令头").append("]:\t[").append(this.getCommandHeader()).append("]\n");
		
		sb.append("[").append("CommandType").append("]:\t\t[").append("命令类型").append("]:\t[").append(this.getCommandType()).append("]\n");
		
		sb.append("[").append("MsgBlock").append("]:\t\t[").append("报文块标志").append("]:\t[").append(this.getMsgBlock()).append("]\n");
		
		sb.append("[").append("KeyType").append("]:\t\t[").append("密钥类型").append("]:\t[").append(this.getKeyType()).append("]\n");
		
		sb.append("[").append("KeyLengthType").append("]:\t[").append("密钥长度").append("]:\t[").append(this.getKeyLengthType()).append("]\n");
		
		sb.append("[").append("MsgType").append("]:\t\t[").append("数据类型").append("]:\t[").append(this.getMsgType()).append("]\n");
		
		sb.append("[").append("KeyValue").append("]:\t\t[").append("密钥值").append("]:\t[").append(this.getKeyValue()).append("]\n");
		
		sb.append("[").append("EncryptDataLength").append("]:\t[").append("数据长度").append("]:\t[").append(this.getEncryptDataLength()).append("]\n");
		
		sb.append("[").append("EncryptDataValue").append("]:\t[").append("数据值").append("]:\t[").append(this.getEncryptDataValue()).append("]\n");
		
		return sb.toString();
	}
	
	
}
