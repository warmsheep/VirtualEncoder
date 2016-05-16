package org.warmsheep.encoder.bean;


public class CCCommandBean extends CommandBean{
	
	private static final int DOUBLE_KEY_ADD_ONE_LENGTH = 33;//双倍长密钥域长度33（带X）
	private static final int DOUBLE_KEY_LENGTH = 32;		//双倍长密钥域长度32（不带X）
	private static final int SINGLE_KEY_LENGTH = 16;		//单倍长密钥域长度
	
	
	public static CCCommandBean build(String header,String commandType,String commandContent){
		CCCommandBean ccCommandBean = new CCCommandBean();
		ccCommandBean.setCommandHeader(header);
		ccCommandBean.setCommandType(commandType);
		
		int subIndex = 0;
		//源ZPK密钥，双倍长
		if(commandContent.substring(subIndex, subIndex + 1).equalsIgnoreCase("X")){
			ccCommandBean.setSourceZpk(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_ADD_ONE_LENGTH));
		}
		//源ZPK密钥，单倍长
		else {
			ccCommandBean.setSourceZpk(commandContent.substring(subIndex, subIndex += SINGLE_KEY_LENGTH));
		}
		
		//目标ZPK密钥，双倍长
		if(commandContent.substring(subIndex, subIndex + 1).equalsIgnoreCase("X")){
			ccCommandBean.setTargetZpk(commandContent.substring(subIndex, subIndex += DOUBLE_KEY_ADD_ONE_LENGTH));
		} 
		//目标ZPK密钥，单倍长
		else {
			ccCommandBean.setSourceZpk(commandContent.substring(subIndex, subIndex += SINGLE_KEY_LENGTH));
		}
		
		ccCommandBean.setMaxPinLength(commandContent.substring(subIndex, subIndex += MAX_PIN_LENGTH));
		ccCommandBean.setSourcePinBlockEncryptData(commandContent.substring(subIndex, subIndex += SOURCE_PIN_BLOCK_ENCRYPT_DATA_LENGTH));
		ccCommandBean.setSourcePinBlockFormat(commandContent.substring(subIndex, subIndex += SOURCE_PIN_BLOCK_FORMAT_LENGTH));
		ccCommandBean.setTargetPinBlockFormat(commandContent.substring(subIndex, subIndex += TARGET_PIN_BLOCK_FORMAT_LENGTH));
		
		ccCommandBean.setAccountNo(commandContent.substring(subIndex, subIndex += ACCOUNT_NO_LENGTH));
		
		return ccCommandBean;
	}

	private String sourceZpk;		//源ZPK密钥
	private String targetZpk;		//用于加密PIN的目标ZPK密钥
	private String maxPinLength;	//最大PIN长度，取值12
	private String sourcePinBlockEncryptData;	//源PIN BLOCK密文
	private String sourcePinBlockFormat;		//源PIN BLOCK格式
	private String targetPinBlockFormat;		//目标PIN BLOCK格式
	private String accountNo;					//用户主账号
	
	/**
	 * 源ZPK密钥
	 * @return
	 */
	public String getSourceZpk() {
		return sourceZpk;
	}
	/**
	 * 源ZPK密钥
	 * @param sourceZpk
	 */
	public void setSourceZpk(String sourceZpk) {
		this.sourceZpk = sourceZpk;
	}
	/**
	 * 用于加密PIN的目标ZPK密钥
	 * @return
	 */
	public String getTargetZpk() {
		return targetZpk;
	}
	/**
	 * 用于加密PIN的目标ZPK密钥
	 * @param targetZpk
	 */
	public void setTargetZpk(String targetZpk) {
		this.targetZpk = targetZpk;
	}
	/**
	 * 最大PIN长度，取值12
	 * @return
	 */
	public String getMaxPinLength() {
		return maxPinLength;
	}
	/**
	 * 最大PIN长度，取值12
	 * @param maxPinLength
	 */
	public void setMaxPinLength(String maxPinLength) {
		this.maxPinLength = maxPinLength;
	}
	/**
	 * 源PIN BLOCK密文
	 * @return
	 */
	public String getSourcePinBlockEncryptData() {
		return sourcePinBlockEncryptData;
	}
	/**
	 * 源PIN BLOCK密文
	 * @param sourcePinBlockEncryptData
	 */
	public void setSourcePinBlockEncryptData(String sourcePinBlockEncryptData) {
		this.sourcePinBlockEncryptData = sourcePinBlockEncryptData;
	}
	/**
	 * 源PIN BLOCK格式
	 * @return
	 */
	public String getSourcePinBlockFormat() {
		return sourcePinBlockFormat;
	}
	/**
	 * 源PIN BLOCK格式
	 * @param sourcePinBlockFormat
	 */
	public void setSourcePinBlockFormat(String sourcePinBlockFormat) {
		this.sourcePinBlockFormat = sourcePinBlockFormat;
	}
	/**
	 * 目标PIN BLOCK格式
	 * @return
	 */
	public String getTargetPinBlockFormat() {
		return targetPinBlockFormat;
	}
	/**
	 * 目标PIN BLOCK格式
	 * @param targetPinBlockFormat
	 */
	public void setTargetPinBlockFormat(String targetPinBlockFormat) {
		this.targetPinBlockFormat = targetPinBlockFormat;
	}
	/**
	 * 用户主账号
	 * @return
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * 用户主账号
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[").append("CommandHeader").append("]:\t\t[").append("命令头").append("]:\t\t[").append(this.getCommandHeader()).append("]\n");
		
		sb.append("[").append("CommandType").append("]:\t\t\t[").append("命令类型").append("]:\t\t[").append(this.getCommandType()).append("]\n");
		
		sb.append("[").append("SourceZpk").append("]:\t\t\t[").append("源TPK密钥").append("]:\t\t[").append(this.getSourceZpk()).append("]\n");
		
		sb.append("[").append("TargetZpk").append("]:\t\t\t[").append("目标TPK密钥").append("]:\t\t[").append(this.getTargetZpk()).append("]\n");
		
		sb.append("[").append("MaxPinLength").append("]:\t\t\t[").append("最大PIN长度").append("]:\t\t[").append(this.getMaxPinLength()).append("]\n");
		
		sb.append("[").append("SourcePinBlockEncryptData").append("]:\t[").append("源PIN BLOCK密文").append("]:\t[").append(this.getSourcePinBlockEncryptData()).append("]\n");
		
		sb.append("[").append("SourcePinBlockFormat").append("]:\t\t[").append("源PIN BLOCK格式").append("]:\t[").append(this.getSourcePinBlockFormat()).append("]\n");
		
		sb.append("[").append("TargetPinBlockFormat").append("]:\t\t[").append("目标PIN BLOCK格式").append("]:\t[").append(this.getTargetPinBlockFormat()).append("]\n");
		
		sb.append("[").append("getAccountNo").append("]:\t\t\t[").append("用户主账号").append("]:\t\t[").append(this.getAccountNo()).append("]\n");
		
		return sb.toString();
	}
	
	
}
