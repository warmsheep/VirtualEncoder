package org.warmsheep.encoder.security.mac.impl;

import org.warmsheep.encoder.security.des.impl.Des;
import org.warmsheep.encoder.security.exception.MacException;
import org.warmsheep.encoder.security.mac.AbstractMac;
import org.warmsheep.encoder.security.util.ByteUtil;

public class ANSIX919 extends AbstractMac {
		
	private ANSIX99 ansix = new ANSIX99();
	
	@Override
	public byte[] getMac(byte[] src, byte[] tak) throws MacException {
		if(tak == null || tak.length != 16){
			throw new MacException("TAK长度错误[16]");
		}
		byte[] left = new byte[8];
		byte[] right = new byte[8];
		System.arraycopy(tak, 0, left, 0, 8);
		System.arraycopy(tak, 8, right, 0, 8);
		
		byte[] macTemp = ansix.getMac(src, left);
		System.out.println("macTemp="+ByteUtil.bytesToHexString(macTemp));
		

		Des des2 = new Des(right);
		byte[] temp = des2.decrypt(macTemp);
		System.out.println("temp="+ByteUtil.bytesToHexString(temp));
		
		Des des1 = new Des(left);
		byte[] mac = des1.encrypt(temp);
		System.out.println("mac="+ByteUtil.bytesToHexString(mac));
		return mac;
	}
	
	public static void main(String[] args) throws MacException {
		byte[] tak = ByteUtil.hexStringToByte("F76213D3FD26CBAD0BC2755DB0D5F810");
		byte[] src = ByteUtil.hexStringToByte("0200722044C020C0909118628882001030600201000000000000050000121218173800022012340210000636628882001030600201D120452010000595004757313030303237383838313532303030303030303839313536D3078ED63706EDBD0230310011220000010000");
		ANSIX919 ansix919 = new ANSIX919();
		byte[] mac = ansix919.getMac(src, tak);
		String macstr = ByteUtil.bytesToHexString(mac);
		System.out.println("C330D183D7D1564F".equals(macstr));
		System.out.println(macstr);
	}
}
