package org.warmsheep.encoder.security.mac;

import org.warmsheep.encoder.security.exception.MacException;

public abstract class AbstractMac {
	

	public abstract byte[] getMac(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
		throws MacException;

	protected byte[] getExclusiveOR(byte[] a, byte[] b)
		throws MacException
	{
		try
		{
			byte[] c = new byte[8];
			for (int i = 0; i < 8; i++) {
				c[i] = ((byte)(a[i] ^ b[i]));
			}
			return c; } catch (Exception e) {
		}
		throw new MacException("异或运算失败,a = " + a + ", b = " + b);
	}
	
	protected byte[] getEightMultiplesData(byte[] data)
		throws MacException
	{
		try
		{
			byte[] result = (byte[])null;
			int length = data.length;
			int k = length % 8;

			if (k == 0)
			{
				int len = length;
				result = data;
			} else {
				int len = (length / 8 + 1) * 8;
				result = new byte[len];
				for (int i = length; i < len; i++) {
					result[i] = 0;
				}
				System.arraycopy(data, 0, result, 0, length);
			}
			data = (byte[])null;
			return result; } catch (Exception e) {
		}
		throw new MacException("获取8倍长数据失败,data = " + data);
	}
}
