package org.warmsheep.encoder.bean;


public class MSCommandBean extends CommandBean{
	
	public static void main(String[] args) {
		MSCommandBean ms = MSCommandBean.build("00000000", "MS", "0111X0132489825A28733B576EBB168757975002509551004910001353852000000000000001000000010142514122801563030303031313235");
		System.out.println(ms.toString());
		
		Integer i =  Integer.parseInt(ms.getEncryptDataLength());
		System.out.println(i);
		System.out.println(ms.getEncryptDataValue().length());
	}
	

	
	public static MSCommandBean build(String header,String commandType,String commandContent){
		MSCommandBean msCommandBean = new MSCommandBean();
		msCommandBean.setCommandHeader(header);
		msCommandBean.setCommandType(commandType);
		
		int i = 0;
		
		msCommandBean.setMsgBlock(commandContent.substring(i, i += MSG_BLOCK_LENGTH));
		msCommandBean.setKeyType(commandContent.substring(i, i += KEY_TYPE_LENGTH));
		msCommandBean.setKeyLengthType(commandContent.substring(i, i += KEY_LENGTH_TYPE_LENGTH));
		msCommandBean.setMsgType(commandContent.substring(i, i += MSG_TYPE_LENGTH));
		if(msCommandBean.getKeyLengthType().equals("1")){
			msCommandBean.setKeyValue(commandContent.substring(i, i += DOUBLE_KEY_LENGTH));
		} else {
			msCommandBean.setKeyValue(commandContent.substring(i, i += SINGLE_KEY_LENGTH));
		}
		msCommandBean.setEncryptDataLength(Integer.valueOf(commandContent.substring(i, i += DATA_LENGTH), 16).toString());
		msCommandBean.setEncryptDataValue(commandContent.substring(i, i+= (Integer.valueOf(msCommandBean.getEncryptDataLength()) * 2) ) );
		
		return msCommandBean;
	}

	private String msgBlock;
	private String keyType;
	private String keyLengthType;
	private String msgType;
	private String keyValue;
	private String encryptDataLength;
	private String encryptDataValue;
	public String getMsgBlock() {
		return msgBlock;
	}
	public void setMsgBlock(String msgBlock) {
		this.msgBlock = msgBlock;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	public String getKeyLengthType() {
		return keyLengthType;
	}
	public void setKeyLengthType(String keyLengthType) {
		this.keyLengthType = keyLengthType;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getEncryptDataLength() {
		return encryptDataLength;
	}
	public void setEncryptDataLength(String encryptDataLength) {
		this.encryptDataLength = encryptDataLength;
	}
	public String getEncryptDataValue() {
		return encryptDataValue;
	}
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
