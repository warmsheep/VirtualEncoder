package org.warmsheep.encoder.bean;



public class FACommandBean extends CommandBean{
	public static final int DOUBLE_KEY_ADD_ONE_LENGTH = 33;		//双倍长密钥域长度33（带X）
	public static final int DOUBLE_KEY_LENGTH = 32;		//双倍长密钥域长度32（不带X）
	public static final int SINGLE_KEY_LENGTH = 16;		//单倍长密钥域长度
	public static final int SEPARATOR_LENGTH = 1;		//分隔域长度
	public static final int ZMK_KEY_FLAG_LENGTH = 1;	//ZMK密钥标识域长度
	public static final int LMK_KEY_FLAG_LENGTH = 1;	//ZMK密钥标识域长度
	public static final int CHECK_VALUE_FLAG_LENGTH = 1;	//密钥校验值标志域长度
	
	public static FACommandBean build(String header,String commandType,String commandContent){
		FACommandBean faCommandBean = new FACommandBean();
		faCommandBean.setCommandHeader(header);
		faCommandBean.setCommandType(commandType);
		
		int subIndex = 0;
		
		//ZMK密文 双倍长
		if(commandContent.substring(subIndex, subIndex + 1).equalsIgnoreCase("X")){
			faCommandBean.setZmkCipher(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_ADD_ONE_LENGTH));
		} else {
			faCommandBean.setZmkCipher(commandContent.substring(subIndex, subIndex += SINGLE_KEY_LENGTH));
		}
		if(commandContent.substring(subIndex, subIndex + 1).equalsIgnoreCase("X")){
			faCommandBean.setKeyOnZmk(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_ADD_ONE_LENGTH));
		} else {
			faCommandBean.setKeyOnZmk(commandContent.substring(subIndex, subIndex += SINGLE_KEY_LENGTH));
		}
		
		
		if(commandContent.length() >= subIndex + SEPARATOR_LENGTH){
			faCommandBean.setSeparator(commandContent.substring(subIndex, subIndex += SEPARATOR_LENGTH));
		}
		if(commandContent.length() >= subIndex + ZMK_KEY_FLAG_LENGTH){
			faCommandBean.setZmkKeyFlag(commandContent.substring(subIndex, subIndex += ZMK_KEY_FLAG_LENGTH));
		}
		if(commandContent.length() >= subIndex + LMK_KEY_FLAG_LENGTH){
			faCommandBean.setLmkKeyFlag(commandContent.substring(subIndex, subIndex += LMK_KEY_FLAG_LENGTH));
		}
		if(commandContent.length() >= subIndex + CHECK_VALUE_FLAG_LENGTH){
			faCommandBean.setCheckValueFlag(commandContent.substring(subIndex, subIndex += CHECK_VALUE_FLAG_LENGTH));
		}
		
		return faCommandBean;
	}
	
	private String zmkCipher;	//ZMK密文
	private String keyOnZmk;	//ZMK下加密的密文
	private String separator;	//分隔符
	private String zmkKeyFlag;	//ZMK下加密的密钥标识
	private String lmkKeyFlag;	//LMK下加密的密钥标识
	private String checkValueFlag;	//密钥校验值计算标识 0 KCV16H; 1 KCV 6H
	
	/**
	 * ZMK密文
	 * @return
	 */
	public String getZmkCipher() {
		return zmkCipher;
	}
	/**
	 * ZMK密文
	 * @param zmkCipher
	 */
	public void setZmkCipher(String zmkCipher) {
		this.zmkCipher = zmkCipher;
	}
	/**
	 * ZMK下加密的密文
	 * @return
	 */
	public String getKeyOnZmk() {
		return keyOnZmk;
	}
	/**
	 * ZMK下加密的密文
	 * @param keyOnZmk
	 */
	public void setKeyOnZmk(String keyOnZmk) {
		this.keyOnZmk = keyOnZmk;
	}
	/**
	 * 分隔符
	 * @return
	 */
	public String getSeparator() {
		return separator;
	}
	/**
	 * 分隔符
	 * @param separator
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	/**
	 * ZMK下加密的密钥标识
	 * @return
	 */
	public String getZmkKeyFlag() {
		return zmkKeyFlag;
	}
	/**
	 * ZMK下加密的密钥标识
	 * @param zmkKeyFlag
	 */
	public void setZmkKeyFlag(String zmkKeyFlag) {
		this.zmkKeyFlag = zmkKeyFlag;
	}
	/**
	 * LMK下加密的密钥标识
	 * @return
	 */
	public String getLmkKeyFlag() {
		return lmkKeyFlag;
	}
	/**
	 * LMK下加密的密钥标识
	 * @param lmkKeyFlag
	 */
	public void setLmkKeyFlag(String lmkKeyFlag) {
		this.lmkKeyFlag = lmkKeyFlag;
	}
	/**
	 * 密钥校验值计算标识 0 KCV16H; 1 KCV 6H
	 * @return
	 */
	public String getCheckValueFlag() {
		return checkValueFlag;
	}
	/**
	 * 密钥校验值计算标识 0 KCV16H; 1 KCV 6H
	 * @param checkValueFlag
	 */
	public void setCheckValueFlag(String checkValueFlag) {
		this.checkValueFlag = checkValueFlag;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[").append("CommandHeader").append("]:\t[").append("命令头").append("]:\t[").append(this.getCommandHeader()).append("]\n");
		
		sb.append("[").append("CommandType").append("]:\t\t[").append("命令类型").append("]:\t[").append(this.getCommandType()).append("]\n");
		
		sb.append("[").append("ZmkCipher").append("]:\t\t[").append("ZMK密文").append("]:\t[").append(this.getZmkCipher()).append("]\n");
		
		sb.append("[").append("KeyOnZmk").append("]:\t[").append("密钥密文").append("]:\t[").append(this.getKeyOnZmk()).append("]\n");
		
		sb.append("[").append("Separator").append("]:\t\t[").append("分隔符").append("]:\t[").append(this.getSeparator()).append("]\n");
		
		sb.append("[").append("ZmkKeyFlag").append("]:\t\t[").append("ZMK密钥标识").append("]:\t[").append(this.getZmkKeyFlag()).append("]\n");
		
		sb.append("[").append("LmkKeyFlag").append("]:\t[").append("LMK密钥标识").append("]:\t[").append(this.getLmkKeyFlag()).append("]\n");
		
		sb.append("[").append("CheckValueFlag").append("]:\t[").append("校验值").append("]:\t[").append(this.getCheckValueFlag()).append("]\n");
		
		return sb.toString();
	}
}
