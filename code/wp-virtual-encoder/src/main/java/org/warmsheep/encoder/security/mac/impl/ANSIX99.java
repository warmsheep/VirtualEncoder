package org.warmsheep.encoder.security.mac.impl;

import org.warmsheep.encoder.security.des.impl.Des;
import org.warmsheep.encoder.security.exception.MacException;
import org.warmsheep.encoder.security.mac.AbstractMac;
import org.warmsheep.encoder.security.util.ByteUtil;

public class ANSIX99 extends AbstractMac {
	public byte[] getMac(byte[] src, byte[] tak) throws MacException {
		if ((src == null) || (src.length == 0))
			throw new MacException("计算MAC的数据为空, src = " + src);
		if (tak == null)
			throw new MacException("TAK为空");
		if (tak.length != 8)
			throw new MacException("TAK的长度有误[" + tak.length + "],期望值[8]");
		try {
			src = getEightMultiplesData(src);
			int dataLen = src.length;
			int groupLen = dataLen / 8;
			byte[][] body = new byte[groupLen][8];
			int index = 0;
			for (int i = 0; i < groupLen; i++) {
				System.arraycopy(src, index, body[i], 0, 8);
				index += 8;
			}
			Des des = new Des(tak);
			byte[] zero = new byte[8];
			for (int i = 0; i < groupLen; i++) {
				zero = getExclusiveOR(body[i], zero);
				zero = des.encrypt(zero);
			}
			body = (byte[][]) null;
			return zero;
		} catch (Exception e) {
			System.err.println(e);
			throw new MacException("计算MAC出错,原因:" + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		byte[] key = ByteUtil.hexStringToByte("F76213D3FD26CBAD");
		byte[] src = ByteUtil
				.hexStringToByte("0200722044C020C0909118628882001030600201000000000000050000121218173800022012340210000636628882001030600201D120452010000595004757313030303237383838313532303030303030303839313536D3078ED63706EDBD0230310011220000010000");
		ANSIX99 ansix99 = new ANSIX99();
		byte[] res = ansix99.getMac(src, key);
		String restr = ByteUtil.bytesToHexString(res);
		System.out.println("2C8989B7028CE438".equals(restr));
		System.out.println(restr);
	}
}
