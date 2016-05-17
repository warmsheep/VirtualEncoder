package org.warmsheep.encoder.security.des.impl;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.warmsheep.encoder.security.des.AbstractDes;
import org.warmsheep.encoder.security.util.ByteUtil;

public class DESede extends AbstractDes {
	private byte[] keybyte;
	private String mode = "DESede/ECB/NoPadding";

	private DESede(byte[] key) {
		this.keybyte = key;
	}

	public static DESede newInstance24(byte[] key) {
		if ((key != null) && (key.length == 24)) {
			return new DESede(key);
		}
		System.err.println("密钥长度有误,期望值[24]");
		return null;
	}

	public static DESede newInstance16(byte[] key) {
		if ((key != null) && (key.length == 16)) {
			byte[] b = new byte[24];
			System.arraycopy(key, 0, b, 0, 16);
			System.arraycopy(key, 0, b, 16, 8);
			key = (byte[]) null;
			return new DESede(b);
		}
		System.err.println("密钥长度有误,期望值[16]");
		return null;
	}

	public static DESede newInstance8(byte[] key) {
		if ((key != null) && (key.length == 8)) {
			byte[] b = new byte[24];
			System.arraycopy(key, 0, b, 0, 8);
			System.arraycopy(key, 0, b, 8, 8);
			System.arraycopy(key, 0, b, 16, 8);
			key = (byte[]) null;
			return new DESede(b);
		}
		System.err.println("密钥长度有误,期望值[8]");
		return null;
	}

	public byte[] encrypt(byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(this.keybyte, "DESede");

			Cipher c1 = Cipher.getInstance(this.mode);
			c1.init(1, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	public byte[] decrypt(byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(this.keybyte, "DESede");

			Cipher c1 = Cipher.getInstance(this.mode);
			c1.init(2, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public static void main(String[] args) {
		byte[] key = ByteUtil.hexStringToByte("12345678901234567890123456789012");
		byte[] src = ByteUtil.hexStringToByte("1234567890ABCDEF");
		DESede des = newInstance16(key);
		byte[] res = des.encrypt(src);
		String restr = ByteUtil.bytesToHexString(res);
		System.out.println("ABF803B6E79765C8".equals(restr));
		System.out.println(restr);

		src = ByteUtil.hexStringToByte("ABF803B6E79765C8");
		res = des.decrypt(src);
		restr = ByteUtil.bytesToHexString(res);
		System.out.println("1234567890ABCDEF".equals(restr));
		System.out.println(restr);
	}
}
