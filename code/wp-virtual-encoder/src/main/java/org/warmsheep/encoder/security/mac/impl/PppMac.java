package org.warmsheep.encoder.security.mac.impl;

import org.warmsheep.encoder.security.des.impl.Des;
import org.warmsheep.encoder.security.exception.MacException;
import org.warmsheep.encoder.security.mac.AbstractMac;
import org.warmsheep.encoder.security.util.ByteUtil;

public class PppMac extends AbstractMac {
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
			byte[] zero = new byte[8];
			for (int i = 0; i < groupLen; i++) {
				zero = getExclusiveOR(body[i], zero);
			}
			body = (byte[][]) null;
			String bodyXor = ByteUtil.bytesToHexString(zero);
			zero = (byte[]) null;
			byte[] bodyXor_1 = bodyXor.substring(0, 8).getBytes();

			Des des = new Des(tak);

			byte[] bodyXor_encrypt = des.encrypt(bodyXor_1);
			bodyXor_1 = (byte[]) null;
			byte[] bodyXor_2 = bodyXor.substring(8, 16).getBytes();
			byte[] keyXor = getExclusiveOR(bodyXor_encrypt, bodyXor_2);
			bodyXor_encrypt = (byte[]) null;
			bodyXor_2 = (byte[]) null;

			byte[] keyXor_encrypt = des.encrypt(keyXor);
			keyXor = (byte[]) null;
			String keyXor_encrypt_str = ByteUtil.bytesToHexString(keyXor_encrypt);
			return keyXor_encrypt_str.substring(0, 8).getBytes();
		} catch (Exception e) {
			System.err.println(e);
			throw new MacException("计算MAC出错,原因:" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		byte[] key = ByteUtil.hexStringToByte("F72645892C737597");
		byte[] data = ByteUtil
				.hexStringToByte("02003020048020C10013190000000000000001000008002181376013822000606913476D12045201000059500032313032333430373939393434303134383132303030300035504147413132333420202020202020202020202020202020202020203030303030302300112200000200100003303031");
		try {
			byte[] mac = new PppMac().getMac(data, key);
			System.out.println("MAC:" + ByteUtil.bytesToHexString(mac));
			System.out.println("MAC:4546393742323830");
		} catch (MacException e) {
			e.printStackTrace();
		}
	}
}
