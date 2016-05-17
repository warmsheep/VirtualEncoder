package org.warmsheep.encoder.security.des.impl;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.warmsheep.encoder.security.des.AbstractDes;
import org.warmsheep.encoder.security.util.ByteUtil;

public class Des extends AbstractDes {
	private byte[] key;
	private String mode = "DES/ECB/NoPadding";

	public Des(byte[] _key) {
		this.key = _key;
		if (this.key == null) {
			System.err.println("密钥不能为空");
			return;
		}
		if (this.key.length != 8)
			System.err.println("密钥长度[" + this.key.length + "]有误,期望值[8]");
	}

	public byte[] encrypt(byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(this.key, "DES");
			Cipher c1 = Cipher.getInstance(this.mode);
			c1.init(1, deskey);
			return c1.doFinal(ByteUtil.getMultiples(src, 8, (byte) 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] decrypt(byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(this.key, "DES");
			Cipher c1 = Cipher.getInstance(this.mode);
			c1.init(2, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		byte[] key = ByteUtil.hexStringToByte("F76213D3FD26CBAD");
		byte[] src = ByteUtil.hexStringToByte("1A6CCAE293B502E9");
		Des des = new Des(key);
		byte[] res = des.encrypt(src);
		String restr = ByteUtil.bytesToHexString(res);
		System.out.println("C330D183D7D1564F".equals(restr));
		System.out.println(restr);

		src = ByteUtil.hexStringToByte("C330D183D7D1564F");
		res = des.decrypt(src);
		restr = ByteUtil.bytesToHexString(res);
		System.out.println("1A6CCAE293B502E9".equals(restr));
		System.out.println(restr);
	}
}
