package org.warmsheep.encoder.actor.processor;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.transaction.Context;
import org.warmsheep.encoder.actor.AbsActor;
import org.warmsheep.encoder.bean.CCCommandBean;
import org.warmsheep.encoder.constants.KeyConstants;
import org.warmsheep.encoder.constants.RespCmdType;
import org.warmsheep.encoder.ic.RespCodeIC;
import org.warmsheep.encoder.ic.TxnIC;
import org.warmsheep.encoder.security.util.EncryptUtil;
import org.warmsheep.encoder.security.util.OddEventCheckUtil;
import org.warmsheep.encoder.security.util.PinUtil;

/**
 * CC指令处理器
 * 
 */
public class CCProcessor extends AbsActor {

	
	@Override
	public int prepare(long id, Serializable serializable) {
		Context context = (Context) serializable;
		try {
			ISOMsg reqMsg = (ISOMsg) context.get(TxnIC.MSG_HSM);
			String header = reqMsg.getString(0);
			String commandType = reqMsg.getString(1);
			String requestData = reqMsg.getString(2);
			
			CCCommandBean ccCommandBean = CCCommandBean.build(header, commandType, requestData);
			
			String sourceTpkClearText = null;
			String sourceEncryptKeyValue = null;
			//密钥第一位为X
			if(ccCommandBean.getSourceZpk().substring(0,1).equalsIgnoreCase("X")){
				sourceEncryptKeyValue = ccCommandBean.getSourceZpk().substring(1);
			} 
			//密钥第一位不为X
			else {
				sourceEncryptKeyValue = ccCommandBean.getSourceZpk();
			}
			//解密密钥
			sourceTpkClearText = EncryptUtil.desDecryptToHex(sourceEncryptKeyValue, KeyConstants.ZPK_001);
			
			
			String targetTpkClearText = null;
			String targetEncryptKeyValue = null;
			//密钥第一位为X
			if(ccCommandBean.getTargetZpk().substring(0,1).equalsIgnoreCase("X")){
				targetEncryptKeyValue = ccCommandBean.getTargetZpk().substring(1);
			} 
			//密钥第一位不为X
			else {
				targetEncryptKeyValue = ccCommandBean.getTargetZpk();
			}
			//解密密钥
			targetTpkClearText = EncryptUtil.desDecryptToHex(targetEncryptKeyValue, KeyConstants.ZPK_001);
			//明文进行奇偶校验
			targetTpkClearText = ISOUtil.hexString(OddEventCheckUtil.parityOfOdd(ISOUtil.hex2byte(targetTpkClearText), 0));
			
			String pinBlockEncryptLength = null;
			String pinBlockEncryptText = null;
			
			
			
			//源加密格式和目标加密格式一致，只进行密钥转换
			if(ccCommandBean.getSourcePinBlockFormat().equals(ccCommandBean.getTargetPinBlockFormat())){
				//解密后的明文
				String pinBlockClearText = EncryptUtil.desDecryptToHex(ccCommandBean.getSourcePinBlockEncryptData(), sourceTpkClearText);
				pinBlockEncryptText = EncryptUtil.desEncryptHexString(pinBlockClearText, targetTpkClearText);
				pinBlockEncryptLength = pinBlockClearText.substring(0, 2);
			} else {
				//解密后的明文
				String pinBlockClearText = EncryptUtil.desDecryptToHex(ccCommandBean.getSourcePinBlockEncryptData(), sourceTpkClearText);
				//带卡号加密，解出原始密码
				if(ccCommandBean.getSourcePinBlockFormat().equals("01")){
					pinBlockClearText = ISOUtil.hexString(PinUtil.reverseByPAN(ccCommandBean.getAccountNo(), pinBlockClearText));
				}
				pinBlockEncryptLength = pinBlockClearText.substring(0, 2);
				if(ccCommandBean.getTargetPinBlockFormat().equals("01")){
					pinBlockClearText = ISOUtil.hexString(PinUtil.processByPAN(pinBlockClearText, ccCommandBean.getAccountNo()));
				}
				pinBlockEncryptText = EncryptUtil.desEncryptHexString(pinBlockClearText, targetTpkClearText);
			}
			
			if(StringUtils.isNotBlank(pinBlockEncryptText)){
				context.put(TxnIC.RESULT_TYPE, RespCmdType.CD);
				context.put(TxnIC.RESULT_CODE, RespCodeIC.SUCCESS);
				context.put(TxnIC.RESULT_DATA, pinBlockEncryptLength + pinBlockEncryptText.toUpperCase() + ccCommandBean.getTargetPinBlockFormat());
			} else {
				context.put(TxnIC.RESULT_TYPE, RespCmdType.CD);
				//TODO待实现
				context.put(TxnIC.RESULT_CODE, RespCodeIC.FORMAT_ERROR);
			}
			
			return PREPARED | NO_JOIN;
		} catch (Exception e) {
			logger.error("MS指令处理出现异常", e);
			context.put(TxnIC.RESULT_CODE, RespCodeIC.OTHER_ERROR);
			return ABORTED | NO_JOIN;
		}
	}
	
	
}
