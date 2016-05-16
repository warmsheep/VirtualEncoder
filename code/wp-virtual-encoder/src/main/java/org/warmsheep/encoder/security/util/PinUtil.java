package org.warmsheep.encoder.security.util;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

public class PinUtil {

	
	 /**
	* PIN加密的主帐号
	* @param accountNo
	* @return
	*/
	public static byte[] getPAN(String accountNo) {
		int len = accountNo.length();
		byte[] tempBytes = accountNo.substring(len < 13 ? 0 : len - 13, len - 1).getBytes();
		byte[] accountNoBytes = new byte[12];
		for (int i = 0; i < 12; i++) {
		 accountNoBytes[i] = (i <= tempBytes.length ? tempBytes[i] : (byte) 0x00);
		}
		byte encode[] = new byte[8];
		encode[0] = (byte) 0x00;
		encode[1] = (byte) 0x00;
		encode[2] = (byte) uniteBytes(accountNoBytes[0], accountNoBytes[1]);
		encode[3] = (byte) uniteBytes(accountNoBytes[2], accountNoBytes[3]);
		encode[4] = (byte) uniteBytes(accountNoBytes[4], accountNoBytes[5]);
		encode[5] = (byte) uniteBytes(accountNoBytes[6], accountNoBytes[7]);
		encode[6] = (byte) uniteBytes(accountNoBytes[8], accountNoBytes[9]);
		encode[7] = (byte) uniteBytes(accountNoBytes[10], accountNoBytes[11]);
		return encode;
	}
	
	/**
	 *  对pin进行处理
	 * @param pin
	 * @return
	 */
	public static byte[] procPin(String pin) {
		if(pin.length() == 16){
			return ISOUtil.hex2byte(pin);
		}
		byte[] pinBytes = pin.getBytes();
		byte[] encode = new byte[8];
		encode[0] = (byte) 0x06;
		encode[1] = (byte) uniteBytes(pinBytes[0], pinBytes[1]);
		encode[2] = (byte) uniteBytes(pinBytes[2], pinBytes[3]);
		encode[3] = (byte) uniteBytes(pinBytes[4], pinBytes[5]);
		encode[4] = (byte) 0xFF;
		encode[5] = (byte) 0xFF;
		encode[6] = (byte) 0xFF;
		encode[7] = (byte) 0xFF;
		return encode;
	}
	
	public static byte[] process(String pin, String accountNo) {
		byte[] pinBytes = procPin(pin);
		byte[] accountNoBytes = getPAN(accountNo);
		byte[] resultBytes = new byte[8];
		//PIN BLOCK 格式等于 PIN 按位异或 主帐号;
		for (int i = 0; i < 8; i++) {
			resultBytes[i] = (byte) (pinBytes[i] ^ accountNoBytes[i]);
		}
		return resultBytes;
	}
	
	public static byte[] processByPAN(String pin,String pan){
		try {
			byte[] pinBytes = procPin(pin);
			pan = ISOUtil.padleft(pan, 16, '0');
			byte[] accountNoBytes = ISOUtil.hex2byte(pan);
			byte[] resultBytes = new byte[8];
			//PIN BLOCK 格式等于 PIN 按位异或 主帐号;
			for (int i = 0; i < 8; i++) {
				resultBytes[i] = (byte) (pinBytes[i] ^ accountNoBytes[i]);
			}
			return resultBytes;
		} catch (ISOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] reverseByPAN(String pan,String xorValue){
		try {
			pan = ISOUtil.padleft(pan, 16, '0');
			byte[] accountNoBytes = ISOUtil.hex2byte(pan);
			byte[] xorValueByte = ISOUtil.hex2byte(xorValue);
			byte arrRet[] = new byte[8];
			for (int i = 0; i < 8; i++) {
				arrRet[i] = (byte) (accountNoBytes[i] ^ xorValueByte[i]);
			}
			return arrRet;
		} catch (ISOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] reverseByAccountNo(String accountNo,String xorValue){
		byte[] accountNoBytes = getPAN(accountNo);
		byte[] xorValueByte = ISOUtil.hex2byte(xorValue);
		byte arrRet[] = new byte[8];
		for (int i = 0; i < 8; i++) {
			arrRet[i] = (byte) (accountNoBytes[i] ^ xorValueByte[i]);
		}
		return arrRet;
	}
	
	/**
	* 
	* @param src0
	* @param src1
	* @return
	*/
	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}
}
