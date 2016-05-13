package org.warmsheep.encoder.security.mac.impl;

import org.paychina.common.security.exception.MacException;
import org.paychina.common.security.utils.ByteUtil;

public class MacTest {
	public static void main(String[] args) throws MacException {
		String data = "303830302030313033313031363530203030353734392031363030303030303030303030303030203130312030383134303333303535";
		byte[] src = ByteUtil.hexStringToByte(data);
		byte[] tak = ByteUtil.hexStringToByte("CB4C3D7A40570B7A34DC97C1EC548A15");
		EcardMac an = new EcardMac();
		byte[] mac = an.getMac(src, tak);
		System.out.println(ByteUtil.bytesToHexString(mac));
		System.out.println("9A1C035B");
	}
}
