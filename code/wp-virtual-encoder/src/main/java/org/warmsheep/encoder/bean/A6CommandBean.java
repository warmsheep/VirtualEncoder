package org.warmsheep.encoder.bean;



public class A6CommandBean extends CommandBean{
	
	private static final int KEY_TYPE_LENGTH = 3;		//密钥类型域长度
	public static final int DOUBLE_KEY_ADD_ONE_LENGTH = 33;		//双倍长密钥域长度33（带X）
	public static final int DOUBLE_KEY_LENGTH = 32;		//双倍长密钥域长度32（不带X）
	public static final int SINGLE_KEY_LENGTH = 16;		//单倍长密钥域长度
	public static final int KEY_FLAG_LENGTH = 1;		//密钥标志域长度
	
	public static A6CommandBean build(String header,String commandType,String commandContent){
		A6CommandBean a6CommandBean = new A6CommandBean();
		a6CommandBean.setCommandHeader(header);
		a6CommandBean.setCommandType(commandType);
		
		int subIndex = 0;
		
		a6CommandBean.setKeyType(commandContent.substring(subIndex, subIndex += KEY_TYPE_LENGTH));
		
		//ZMK双倍长
		if(commandContent.substring(subIndex, subIndex + 1).equalsIgnoreCase("X")){
			a6CommandBean.setZmkCipher(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_ADD_ONE_LENGTH));
		} else{
			a6CommandBean.setZmkCipher(commandContent.substring(subIndex, subIndex += SINGLE_KEY_LENGTH));
		}
		
		//keyOnZMK双倍长
		if(commandContent.substring(subIndex, subIndex + 1).equalsIgnoreCase("X")){
			a6CommandBean.setKeyOnZmk(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_ADD_ONE_LENGTH));
		} else {
			a6CommandBean.setKeyOnZmk(commandContent.substring(subIndex, subIndex += SINGLE_KEY_LENGTH));
		}
		
		a6CommandBean.setKeyFlag(commandContent.substring(subIndex, subIndex += KEY_FLAG_LENGTH));
		
		return a6CommandBean;
	}

	
	private String keyType;	//密钥类型
	private String zmkCipher;	//ZMK密文
	private String keyOnZmk;	//ZMK下加密的密钥密文
	private String keyFlag;		//密钥标志
	
	/**
	 * 密钥类型
	 * @return
	 */
	public String getKeyType() {
		return keyType;
	}
	/**
	 * 密钥类型
	 * @param keyType
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
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
	 * ZMK下加密的密钥密文
	 * @return
	 */
	public String getKeyOnZmk() {
		return keyOnZmk;
	}
	/**
	 * ZMK下加密的密钥密文
	 * @param keyOnZmk
	 */
	public void setKeyOnZmk(String keyOnZmk) {
		this.keyOnZmk = keyOnZmk;
	}
	/**
	 * 密钥标志
	 * @return
	 */
	public String getKeyFlag() {
		return keyFlag;
	}
	/**
	 * 密钥标志
	 * @param keyFlag
	 */
	public void setKeyFlag(String keyFlag) {
		this.keyFlag = keyFlag;
	}
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[").append("CommandHeader").append("]:\t[").append("命令头").append("]:\t[").append(this.getCommandHeader()).append("]\n");
		
		sb.append("[").append("CommandType").append("]:\t\t[").append("命令类型").append("]:\t[").append(this.getCommandType()).append("]\n");
		
		sb.append("[").append("KeyType").append("]:\t\t[").append("密钥类型").append("]:\t[").append(this.getKeyType()).append("]\n");
		
		sb.append("[").append("ZmkCipher").append("]:\t\t[").append("ZMK密文").append("]:\t[").append(this.getZmkCipher()).append("]\n");
		
		sb.append("[").append("KeyOnZmk").append("]:\t[").append("密钥密文").append("]:\t[").append(this.getKeyOnZmk()).append("]\n");
		
		sb.append("[").append("KeyFlag").append("]:\t\t[").append("密钥标识").append("]:\t[").append(this.getKeyFlag()).append("]\n");
		
		return sb.toString();
	}
}
