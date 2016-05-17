package org.warmsheep.encoder.bean;



public class A0CommandBean extends CommandBean{
	
	//A0指令相关
	private static final int GENERATE_MODE_LENGTH = 1;	//产生模式域长度
	private static final int KEY_TYPE_LENGTH = 3;		//密钥类型域长度
	private static final int LMK_KEY_FLAG_LENGTH = 1;	//lmk标识域长度
	private static final int ZMK_KEY_FLAG_LENGTH = 1;	//lmk标识域长度
	
	public static final int DOUBLE_KEY_ADD_ONE_LENGTH = 33;		//双倍长密钥域长度33（带X）
	public static final int DOUBLE_KEY_LENGTH = 32;		//双倍长密钥域长度32（不带X）
	public static final int SINGLE_KEY_LENGTH = 16;		//单倍长密钥域长度
	
	public static A0CommandBean build(String header,String commandType,String commandContent){
		A0CommandBean a0CommandBean = new A0CommandBean();
		a0CommandBean.setCommandHeader(header);
		a0CommandBean.setCommandType(commandType);
		
		int subIndex = 0;
		
		a0CommandBean.setGenerateMode(commandContent.substring(subIndex, subIndex += GENERATE_MODE_LENGTH));
		a0CommandBean.setKeyType(commandContent.substring(subIndex, subIndex += KEY_TYPE_LENGTH));
		a0CommandBean.setLmkKeyFlag(commandContent.substring(subIndex, subIndex += LMK_KEY_FLAG_LENGTH));
		
		if(a0CommandBean.getGenerateMode().equals("1")){
			if(commandContent.substring(subIndex, subIndex + 1).equalsIgnoreCase("X")){
				a0CommandBean.setZmkCipher(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_ADD_ONE_LENGTH));
			} else {
				a0CommandBean.setZmkCipher(commandContent.substring(subIndex, subIndex += SINGLE_KEY_LENGTH));
			}
			
			a0CommandBean.setZmkKeyFlag(commandContent.substring(subIndex, subIndex += ZMK_KEY_FLAG_LENGTH));
		}
		
		return a0CommandBean;
	}

	private String generateMode;		//产生模式0 – 产生密钥 1 – 产生密钥并在ZMK 下加密
	private String keyType;				//密钥类型
	private String lmkKeyFlag;			//LMK密钥标识
	private String zmkCipher;			//ZMK密文
	private String zmkKeyFlag;			//ZMK密钥标识
	
	/**
	 * //产生模式0 – 产生密钥 1 – 产生密钥并在ZMK 下加密
	 * @return
	 */
	public String getGenerateMode() {
		return generateMode;
	}
	/**
	 * //产生模式0 – 产生密钥 1 – 产生密钥并在ZMK 下加密
	 * @param generateMode
	 */
	public void setGenerateMode(String generateMode) {
		this.generateMode = generateMode;
	}
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
	 * LMK密钥标识
	 * @return
	 */
	public String getLmkKeyFlag() {
		return lmkKeyFlag;
	}
	/**
	 * LMK密钥标识
	 * @param lmkKeyFlag
	 */
	public void setLmkKeyFlag(String lmkKeyFlag) {
		this.lmkKeyFlag = lmkKeyFlag;
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
	 * ZMK密钥标识
	 * @return
	 */
	public String getZmkKeyFlag() {
		return zmkKeyFlag;
	}
	/**
	 * ZMK密钥标识
	 * @param zmkKeyFlag
	 */
	public void setZmkKeyFlag(String zmkKeyFlag) {
		this.zmkKeyFlag = zmkKeyFlag;
	}
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[").append("CommandHeader").append("]:\t[").append("命令头").append("]:\t[").append(this.getCommandHeader()).append("]\n");
		
		sb.append("[").append("CommandType").append("]:\t\t[").append("命令类型").append("]:\t[").append(this.getCommandType()).append("]\n");
		
		sb.append("[").append("GenerateMode").append("]:\t\t[").append("密钥产生模式").append("]:\t[").append(this.getGenerateMode()).append("]\n");
		
		sb.append("[").append("KeyType").append("]:\t\t[").append("密钥类型").append("]:\t[").append(this.getKeyType()).append("]\n");
		
		sb.append("[").append("LmkKeyFlag").append("]:\t[").append("LMK密钥标识").append("]:\t[").append(this.getLmkKeyFlag()).append("]\n");
		
		sb.append("[").append("ZmkCipher").append("]:\t\t[").append("ZMK密文").append("]:\t[").append(this.getZmkCipher()).append("]\n");
		
		sb.append("[").append("ZmkKeyFlag").append("]:\t\t[").append("ZMK密钥标识").append("]:\t[").append(this.getZmkKeyFlag()).append("]\n");
		
		return sb.toString();
	}
	
}
