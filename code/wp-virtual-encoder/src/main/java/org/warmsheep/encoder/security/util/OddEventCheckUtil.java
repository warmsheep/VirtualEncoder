package org.warmsheep.encoder.security.util;

import org.jpos.iso.ISOUtil;

/**
 * 奇偶校验算法工具类
 * @author Warmsheep
 *
 */
public class OddEventCheckUtil {

	public static void main(String args[]) throws Exception {
		String s = "2222222222222222";
		byte[] bs = parityOfOdd(ISOUtil.hex2byte(s), 0);
		System.out.println(ISOUtil.hexString(bs));
	}

	public static byte[] parityOfOdd(byte[] bytes, int parity) throws Exception {
		if (bytes == null || bytes.length % 8 != 0) {
			throw new Exception("数据错误!");
		}
		if (!(parity == 0 || parity == 1)) {
			throw new Exception("参数错误!");
		}
		byte[] _bytes = bytes;
		String s; // 字节码转二进制字符串
		char[] cs; // 二进制字符串转字符数组
		int count; // 为1的总个数
		boolean lastIsOne; // 最后一位是否为1
		for (int i = 0; i < _bytes.length; i++) {
			// 初始化参数
			s = Integer.toBinaryString((int) _bytes[i]); // 字节码转二进制字符串
			cs = s.toCharArray();// 二进制字符串转字符数组
			count = 0;// 为1的总个数
			lastIsOne = false;// 最后一位是否为1
			for (int j = 0; j < s.length(); j++) {
				if (cs[j] == '1') {
					count++;
				}
				if (j == (cs.length - 1)) { // 判断最后一位是否为1
					if (cs[j] == '1') {
						lastIsOne = true;
					} else {
						lastIsOne = false;
					}
				}
			}
			// 偶数个1时
			if (count % 2 == parity) {
				// 最后一位为1,变为0
				if (lastIsOne) {
					_bytes[i] = (byte) (_bytes[i] - 0x01);
				} else {
					// 最后一位为0,变为1
					_bytes[i] = (byte) (_bytes[i] + 0x01);
				}
			}
		}
		return _bytes;
	}
}
